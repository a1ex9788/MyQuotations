package a1ex9788.dadm.myquotations.threads;

import java.lang.ref.WeakReference;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.RandomQuotationsActivity;
import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

public class ShowNewRandomQuotationThread extends Thread {

    private static int receivedQuotations = 0;
    private WeakReference<RandomQuotationsActivity> reference;

    public ShowNewRandomQuotationThread(RandomQuotationsActivity activity) {
        super();
        this.reference = new WeakReference<>(activity);
    }

    @Override
    public void run() {
        if (reference == null) {
            return;
        }

        RandomQuotationsActivity activity = reference.get();

        Quotation newQuotation = getNewRandomQuotation(activity);

        activity.runOnUiThread(() -> {
            activity.showNewQuotation(newQuotation);
        });

        if (!existsQuotation(activity, newQuotation)) {
            activity.runOnUiThread(() -> {
                activity.showAddFavouriteQuotationMenuItem();
            });
        }
    }

    private Quotation getNewRandomQuotation(RandomQuotationsActivity activity) {
        return new Quotation(
                String.format(activity.getString(R.string.textView_sampleQuotation), ++receivedQuotations),
                String.format(activity.getString(R.string.textView_sampleAuthor), receivedQuotations));
    }

    private boolean existsQuotation(RandomQuotationsActivity activity, Quotation newQuotation) {
        QuotationDatabase database = QuotationDatabaseAccess.getDatabase(activity);
        return database.existsQuotation(newQuotation.getText());
    }

}
