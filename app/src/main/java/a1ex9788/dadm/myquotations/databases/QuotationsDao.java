package a1ex9788.dadm.myquotations.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import a1ex9788.dadm.myquotations.model.Quotation;

@Dao
public interface QuotationsDao {

    @Insert
    void addQuotation(Quotation quotation);

    @Delete
    void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM " + MySqLiteOpenHelper.QUOTATIONS_TABLE_NAME)
    List<Quotation> getQuotations();

    @Query("DELETE FROM " + MySqLiteOpenHelper.QUOTATIONS_TABLE_NAME)
    void deleteAllQuotations();

}
