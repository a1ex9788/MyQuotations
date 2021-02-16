package a1ex9788.dadm.myquotations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class RandomQuotationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotations);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = preferences.getString(getString(R.string.settingsKey_userName), "");
        userName = userName.isEmpty() ? getString(R.string.textView_noUserName) : userName;
        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(String.format(getString(R.string.textView_refreshQuotation), userName));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random_quotations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                return true;
            case R.id.menu_refresh:
                getNewRandomQuotation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getNewRandomQuotation() {
        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(R.string.textView_sampleQuotation);

        TextView textView_author = findViewById(R.id.textView_author);
        textView_author.setText(R.string.textView_sampleActor);
    }

}