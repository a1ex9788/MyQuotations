package a1ex9788.dadm.myquotations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    public static final String USER_NAME_KEY = "userName";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String storedUserName = preferences.getString(USER_NAME_KEY, "");
        editText = findViewById(R.id.editText_userName);
        editText.setText(storedUserName);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        String userName = editText.getText().toString();

        if (userName == null || userName.isEmpty()) {
            editor.remove(USER_NAME_KEY);
        } else {
            editor.putString(USER_NAME_KEY, userName);
        }

        editor.apply();
    }

}