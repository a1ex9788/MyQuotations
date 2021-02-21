package a1ex9788.dadm.myquotations.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.myquotations.model.Quotation;

public class MySqLiteOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyQuotationsDB",
            QUOTATIONS_TABLE_NAME = QuotationContract.QuotationsTable.CLASS_NAME,
            QUOTATION_COLUMN_ID = QuotationContract.QuotationsTable._ID,
            QUOTATION_COLUMN_TEXT = QuotationContract.QuotationsTable.COLUMN_TEXT,
            QUOTATION_COLUMN_AUTHOR = QuotationContract.QuotationsTable.COLUMN_AUTHOR;
    public static final int DATABASE_VERSION = 1;
    private static MySqLiteOpenHelper instance;

    private MySqLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static MySqLiteOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MySqLiteOpenHelper(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sqlCommandBase = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT);";
            String sqlCommand = String.format(sqlCommandBase, QUOTATIONS_TABLE_NAME, QUOTATION_COLUMN_ID, QUOTATION_COLUMN_TEXT, QUOTATION_COLUMN_AUTHOR);
            db.execSQL(sqlCommand);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Quotation> getQuotations() {
        List<Quotation> quotations = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(QUOTATIONS_TABLE_NAME, new String[]{QUOTATION_COLUMN_TEXT, QUOTATION_COLUMN_AUTHOR},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            quotations.add(new Quotation(cursor.getString(0), cursor.getString(1)));
        }

        cursor.close();
        db.close();

        return quotations;
    }

    public boolean existsQuotation(String quotationText) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(QUOTATIONS_TABLE_NAME, null, QUOTATION_COLUMN_TEXT + "=?",
                new String[]{quotationText}, null, null, null);

        boolean existsQuotation = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return existsQuotation;
    }

    public void addQuotation(String text, String author) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(QUOTATION_COLUMN_TEXT, text);
        contentValues.put(QUOTATION_COLUMN_AUTHOR, author);
        db.insert(QUOTATIONS_TABLE_NAME, null, contentValues);

        db.close();
    }

    public void deleteAllQuotations() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(QUOTATIONS_TABLE_NAME, null, null);

        db.close();
    }

    public void deleteQuotation(String quoteText) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(QUOTATIONS_TABLE_NAME, QUOTATION_COLUMN_TEXT + "=?", new String[]{quoteText});

        db.close();
    }

}
