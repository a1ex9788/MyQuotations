package a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper;

import android.provider.BaseColumns;

import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;

public class QuotationContract {

    private QuotationContract() {
    }

    public class QuotationsTable implements BaseColumns {

        public static final String CLASS_NAME = QuotationDatabaseAccess.QUOTATIONS_TABLE_NAME,
                COLUMN_TEXT = QuotationDatabaseAccess.QUOTATION_COLUMN_TEXT,
                COLUMN_AUTHOR = QuotationDatabaseAccess.QUOTATION_COLUMN_AUTHOR;

    }

}
