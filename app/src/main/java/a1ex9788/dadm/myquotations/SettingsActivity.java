package a1ex9788.dadm.myquotations;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import a1ex9788.dadm.myquotations.databases.room.MyRoomDatabase;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout_settings, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences_settings, rootKey);

            Preference databaseTypePreference = findPreference(getString(R.string.settingsKey_databaseAccess));
            databaseTypePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if (newValue.toString().equals(getString(R.string.settingsOption_sqLiteDatabaseAccess))) {
                    MyRoomDatabase.destroyInstance();
                }

                return true;
            });
        }

    }

}