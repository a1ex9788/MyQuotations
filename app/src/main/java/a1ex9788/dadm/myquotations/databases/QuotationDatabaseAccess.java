package a1ex9788.dadm.myquotations.databases;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import a1ex9788.dadm.myquotations.R;
import a1ex9788.dadm.myquotations.databases.room.MyRoomDatabase;
import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.MySqLiteOpenHelper;

public class QuotationDatabaseAccess {

    public static QuotationDatabase getDatabase(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String databaseType = preferences.getString(context.getString(R.string.settingsKey_databaseAccess), "");

        if (databaseType == null || databaseType.equals(context.getString(R.string.settingsOption_roomDatabaseAccess))) {
            return MyRoomDatabase.getInstance(context).quotationsDao();
        } else {
            return MySqLiteOpenHelper.getInstance(context);
        }
    }

}
