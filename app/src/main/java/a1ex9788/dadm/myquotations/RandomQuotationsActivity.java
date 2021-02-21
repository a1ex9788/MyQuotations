package a1ex9788.dadm.myquotations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import a1ex9788.dadm.myquotations.databases.sqLiteOpenHelper.MySqLiteOpenHelper;

public class RandomQuotationsActivity extends AppCompatActivity {

    private static final String CURRENT_QUOTATION_KEY = "currentQuotation", CURRENT_AUTHOR_KEY = "currentAuthor",
            CURRENT_QUOTATION_NUMBER_KEY = "currentQuotationNumber", ADD_MENU_ITEM_IS_VISIBLE_KEY = "addMenuItemIsVisible";

    private TextView textView_quotation, textView_author;
    private int receivedQuotations = 0;
    private MenuItem addMenuItem;
    private boolean addMenuItemIsVisible = false;

    private MySqLiteOpenHelper database = MySqLiteOpenHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotations);

        textView_quotation = findViewById(R.id.textView_quotation);
        textView_author = findViewById(R.id.textView_author);

        if (savedInstanceState == null) {
            setHelloMessage();
        } else {
            String a = savedInstanceState.getString(CURRENT_QUOTATION_KEY);
            textView_quotation.setText(savedInstanceState.getString(CURRENT_QUOTATION_KEY));
            textView_author.setText(savedInstanceState.getString(CURRENT_AUTHOR_KEY));
            receivedQuotations = savedInstanceState.getInt(CURRENT_QUOTATION_NUMBER_KEY);
            addMenuItemIsVisible = savedInstanceState.getBoolean(ADD_MENU_ITEM_IS_VISIBLE_KEY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random_quotations, menu);
        addMenuItem = menu.findItem(R.id.menu_add);
        addMenuItem.setVisible(addMenuItemIsVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                database.addQuotation(textView_quotation.getText().toString(), textView_author.getText().toString());
                addMenuItem.setVisible(false);
                return true;
            case R.id.menu_refresh:
                String quotationText = getNewRandomQuotation();
                if (database.existsQuotation(quotationText)) {
                    addMenuItem.setVisible(false);
                } else {
                    addMenuItem.setVisible(true);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putString(CURRENT_QUOTATION_KEY, textView_quotation.getText().toString());
        bundle.putString(CURRENT_AUTHOR_KEY, textView_author.getText().toString());
        bundle.putInt(CURRENT_QUOTATION_NUMBER_KEY, receivedQuotations);
        bundle.putBoolean(ADD_MENU_ITEM_IS_VISIBLE_KEY, addMenuItem.isVisible());
    }

    private void setHelloMessage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = preferences.getString(getString(R.string.settingsKey_userName), "");
        userName = userName.isEmpty() ? getString(R.string.textView_noUserName) : userName;
        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(String.format(getString(R.string.textView_refreshQuotation), userName));
    }

    private String getNewRandomQuotation() {
        receivedQuotations++;

        String quotationText = String.format(getString(R.string.textView_sampleQuotation), receivedQuotations);
        textView_quotation.setText(quotationText);
        textView_author.setText(String.format(getString(R.string.textView_sampleAuthor), receivedQuotations));

        return quotationText;
    }

}
