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
    private String text;

    @ColumnInfo(name = MySqLiteOpenHelper.QUOTATION_COLUMN_AUTHOR)
    private String author;

    public Quotation() {
    }

    public Quotation(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
