package a1ex9788.dadm.myquotations.databases.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

@Dao
public abstract class QuotationsDao implements QuotationDatabaseAccess {

    @Insert
    public abstract void addQuotation(Quotation quotation);

    @Delete
    public abstract void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM " + QUOTATIONS_TABLE_NAME + " WHERE " + QUOTATION_COLUMN_TEXT + " =:quotationText")
    public abstract Quotation getQuotation(String quotationText);

    public boolean existsQuotation(String quotationText) {
        return getQuotation(quotationText) != null;
    }

    @Query("SELECT * FROM " + QUOTATIONS_TABLE_NAME)
    public abstract List<Quotation> getQuotations();

    @Query("DELETE FROM " + QUOTATIONS_TABLE_NAME)
    public abstract void deleteAllQuotations();

}
