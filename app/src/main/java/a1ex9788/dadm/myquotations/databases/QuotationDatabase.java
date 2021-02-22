package a1ex9788.dadm.myquotations.databases;

import android.provider.BaseColumns;

import java.util.List;

import a1ex9788.dadm.myquotations.model.Quotation;

public interface QuotationDatabase extends BaseColumns {

    String DATABASE_NAME = "MyQuotationsDB",
            QUOTATION_COLUMN_ID = _ID,
            QUOTATIONS_TABLE_NAME = "QuotationsTable",
            QUOTATION_COLUMN_TEXT = "Text",
            QUOTATION_COLUMN_AUTHOR = "Author";

    int DATABASE_VERSION = 1;

    void addQuotation(Quotation quotation);

    void deleteQuotation(Quotation quotation);

    boolean existsQuotation(String quotationText);

    List<Quotation> getQuotations();

    void deleteAllQuotations();

}
