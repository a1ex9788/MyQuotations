package a1ex9788.dadm.myquotations.threads;

import java.lang.ref.WeakReference;

import a1ex9788.dadm.myquotations.RandomQuotationsActivity;
import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

public class ShowNewRandomQuotationThread extends Thread {

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

        activity.runOnUiThread(() -> {
            activity.hideActionBarAndShowProgressBar();
        });

        Quotation newQuotation = getNewRandomQuotation();

        activity.runOnUiThread(() -> {
            activity.showNewQuotation(newQuotation);
        });

        if (!existsQuotation(activity, newQuotation)) {
            activity.runOnUiThread(() -> {
                activity.showAddFavouriteQuotationMenuItem();
            });
        }
    }

    private Quotation getNewRandomQuotation() {
        return new Quotation("hola", "pene");
    }

    private boolean existsQuotation(RandomQuotationsActivity activity, Quotation newQuotation) {
        QuotationDatabase database = QuotationDatabaseAccess.getDatabase(activity);
        return database.existsQuotation(newQuotation.getText());
    }

}
