package a1ex9788.dadm.myquotations.databases;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.List;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.databases.room.MyRoomDatabase;
import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.MySqLiteOpenHelper;
import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.QuotationContract;
import a1ex9788.dadm.myquotations.model.Quotation;

public interface QuotationDatabaseAccess {

    String DATABASE_NAME = "MyQuotationsDB",
            QUOTATION_COLUMN_ID = QuotationContract.QuotationsTable._ID,
            QUOTATIONS_TABLE_NAME = "QuotationsTable",
            QUOTATION_COLUMN_TEXT = "Text",
            QUOTATION_COLUMN_AUTHOR = "Author";

    int DATABASE_VERSION = 1;

    // This method should be in an specific class because an interface must not know about its implementors.
    static QuotationDatabaseAccess getDatabase(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String databaseType = preferences.getString(context.getString(R.string.settingsKey_databaseAccess), "");

        if (databaseType == null || databaseType.equals(context.getString(R.string.settingsOption_roomDatabaseAccess))) {
            return MyRoomDatabase.getInstance(context).quotationsDao();
        } else {
            return MySqLiteOpenHelper.getInstance(context);
        }
    }

    void addQuotation(Quotation quotation);

    void deleteQuotation(Quotation quotation);

    boolean existsQuotation(String quotationText);

    List<Quotation> getQuotations();

    void deleteAllQuotations();

}
