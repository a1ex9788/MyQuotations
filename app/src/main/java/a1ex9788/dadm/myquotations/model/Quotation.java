package a1ex9788.dadm.myquotations.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.MySqLiteOpenHelper;

@Entity(tableName = MySqLiteOpenHelper.QUOTATIONS_TABLE_NAME)
public class Quotation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MySqLiteOpenHelper.QUOTATION_COLUMN_ID)
    private int id;

    @NonNull
    @ColumnInfo(name = MySqLiteOpenHelper.QUOTATION_COLUMN_TEXT)
    private String quoteText;

    @ColumnInfo(name = MySqLiteOpenHelper.QUOTATION_COLUMN_AUTHOR)
    private String quoteAuthor;

    public Quotation() {
    }

    public Quotation(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

}
