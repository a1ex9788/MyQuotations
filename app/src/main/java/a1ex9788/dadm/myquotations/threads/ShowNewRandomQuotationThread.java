package a1ex9788.dadm.myquotations.threads;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.RandomQuotationsActivity;
import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ShowNewRandomQuotationThread extends Thread {

    private WeakReference<RandomQuotationsActivity> reference;

    public ShowNewRandomQuotationThread(RandomQuotationsActivity activity) {
        super();
        this.reference = new WeakReference<>(activity);
    }

    @Override
    public void run() {
        try {
            if (reference == null) {
                return;
            }

            RandomQuotationsActivity activity = reference.get();

            if (!hasInternetConnection()) {
                showErrorMessage(activity);
                return;
            }

            activity.runOnUiThread(() -> {
                activity.hideActionBarAndShowProgressBar();
            });

            Quotation newQuotation = getNewRandomQuotationFromWebService();

            if (newQuotation == null) {
                showErrorMessage(activity);
                return;
            }

            activity.runOnUiThread(() -> {
                activity.showNewQuotation(newQuotation);
            });

            if (!existsQuotation(activity, newQuotation)) {
                activity.runOnUiThread(() -> {
                    activity.showAddFavouriteQuotationMenuItem();
                });
            }
        } catch (Exception e) {
            if (reference != null) {
                showErrorMessage(reference.get());
            }
        }
    }

    public boolean hasInternetConnection() {
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) reference.get().getSystemService(CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT > 22) {
            final Network activeNetwork = manager.getActiveNetwork();
            if (activeNetwork != null) {
                final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
                result = networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            }
        } else {
            NetworkInfo info = manager.getActiveNetworkInfo();
            result = ((info != null) && (info.isConnected()));
        }

        return result;
    }

    private Quotation getNewRandomQuotationFromWebService() {
        Quotation newQuotation = null;
        RandomQuotationsActivity activity = reference.get();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String language = preferences.getString(activity.getString(R.string.settingsKey_quotationsLanguage), activity.getString(R.string.settingsOption_englishQuotationsLanguage_internal));
        String httpMethod = preferences.getString(activity.getString(R.string.settingsKey_httpMethod), activity.getString(R.string.settingsOption_getHttpMethod_internal));
        boolean useGetHttpMethod = httpMethod.equals(activity.getString(R.string.settingsOption_getHttpMethod_internal));

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendEncodedPath("1.0/");

        if (useGetHttpMethod) {
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("lang", language);
        }

        String body = "method=getQuote&format=json&lang=" + language;

        try {
            URL url = new URL(builder.build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setDoInput(true);

            if (!useGetHttpMethod) {
                connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();
                writer.close();
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

                Gson gson = new Gson();
                newQuotation = gson.fromJson(inputStreamReader, Quotation.class);

                inputStreamReader.close();
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newQuotation;
    }

    private void showErrorMessage(RandomQuotationsActivity activity) {
        Quotation errorQuotation = new Quotation(activity.getString(R.string.textView_errorQuotationText), activity.getString(R.string.textView_errorQuotationAuthor));

        activity.runOnUiThread(() -> {
            activity.showNewQuotation(errorQuotation);
        });
    }

    private boolean existsQuotation(RandomQuotationsActivity activity, Quotation newQuotation) {
        QuotationDatabase database = QuotationDatabaseAccess.getDatabase(activity);
        return database.existsQuotation(newQuotation.getText());
    }

}
