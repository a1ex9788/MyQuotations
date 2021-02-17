package a1ex9788.dadm.myquotations.databases;

import android.provider.BaseColumns;

public class QuotationContract {

    private QuotationContract() {
    }

    public class QuotationsTable implements BaseColumns {

        public static final String COLUMN_TEXT = "Text", COLUMN_AUTHOR = "Author";

    }

}
