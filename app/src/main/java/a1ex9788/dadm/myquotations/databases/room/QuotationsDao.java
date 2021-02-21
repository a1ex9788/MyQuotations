package a1ex9788.dadm.myquotations.databases.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.MySqLiteOpenHelper;
import a1ex9788.dadm.myquotations.model.Quotation;

@Dao
public abstract class QuotationsDao {

    @Insert
    abstract void addQuotation(Quotation quotation);

    @Delete
    abstract void deleteQuotation(Quotation quotation);

    @Query("SELECT * FROM " + MySqLiteOpenHelper.QUOTATIONS_TABLE_NAME)
    abstract List<Quotation> getQuotations();

    @Query("DELETE FROM " + MySqLiteOpenHelper.QUOTATIONS_TABLE_NAME)
    abstract void deleteAllQuotations();

}
