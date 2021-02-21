package a1ex9788.dadm.myquotations.databases.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;

@Database(entities = {Quotation.class}, version = QuotationDatabaseAccess.DATABASE_VERSION)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static MyRoomDatabase myRoomDatabase;

    public static void destroyInstance() {
        if (myRoomDatabase != null && myRoomDatabase.isOpen()) {
            myRoomDatabase.close();
            myRoomDatabase = null;
        }
    }

    public MyRoomDatabase getInstance(Context context) {
        if (myRoomDatabase == null) {
            myRoomDatabase = Room
                    .databaseBuilder(context, MyRoomDatabase.class, QuotationDatabaseAccess.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return myRoomDatabase;
    }

    public abstract QuotationsDao quotationsDao();

}
