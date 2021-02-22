package a1ex9788.dadm.myquotations.threads;

import java.lang.ref.WeakReference;
import java.util.List;

import a1ex9788.dadm.myquotations.FavouriteQuotationsActivity;
import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

public class ShowFavouriteQuotationsThread extends Thread {

    private WeakReference<FavouriteQuotationsActivity> reference;

    public ShowFavouriteQuotationsThread(FavouriteQuotationsActivity activity) {
        super();
        this.reference = new WeakReference<>(activity);
    }

    @Override
    public void run() {
        if (reference == null) {
            return;
        }

        FavouriteQuotationsActivity activity = reference.get();

        QuotationDatabase database = QuotationDatabaseAccess.getDatabase(activity);
        List<Quotation> quotations = database.getQuotations();

        activity.runOnUiThread(() -> {
            activity.showQuotations(quotations);
        });
    }

}
