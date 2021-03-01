package a1ex9788.dadm.myquotations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;
import a1ex9788.dadm.myquotations.threads.ShowNewRandomQuotationThread;

public class RandomQuotationsActivity extends AppCompatActivity {

    private static final String CURRENT_QUOTATION_KEY = "currentQuotation", CURRENT_AUTHOR_KEY = "currentAuthor",
            ADD_MENU_ITEM_IS_VISIBLE_KEY = "addMenuItemIsVisible";

    private TextView textView_quotation, textView_author;
    private MenuItem refreshMenuItem, addMenuItem;
    private boolean addMenuItemIsVisible = false;

    private QuotationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotations);

        textView_quotation = findViewById(R.id.textView_quotation);
        textView_author = findViewById(R.id.textView_author);

        if (savedInstanceState == null) {
            setHelloMessage();
        } else {
            textView_quotation.setText(savedInstanceState.getString(CURRENT_QUOTATION_KEY));
            textView_author.setText(savedInstanceState.getString(CURRENT_AUTHOR_KEY));
            addMenuItemIsVisible = savedInstanceState.getBoolean(ADD_MENU_ITEM_IS_VISIBLE_KEY);
        }

        database = QuotationDatabaseAccess.getDatabase(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.random_quotations, menu);
        refreshMenuItem = menu.findItem(R.id.menu_refresh);
        addMenuItem = menu.findItem(R.id.menu_add);
        addMenuItem.setVisible(addMenuItemIsVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                addFavouriteQuotation();
                return true;
            case R.id.menu_refresh:
                showNewRandomQuotation();
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
        bundle.putBoolean(ADD_MENU_ITEM_IS_VISIBLE_KEY, addMenuItem.isVisible());
    }

    private void setHelloMessage() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = preferences.getString(getString(R.string.settingsKey_userName), "");
        userName = userName.isEmpty() ? getString(R.string.textView_noUserName) : userName;
        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(String.format(getString(R.string.textView_refreshQuotation), userName));
    }

    private void addFavouriteQuotation() {
        addMenuItem.setVisible(false);

        new Thread(() -> {
            database.addQuotation(new Quotation(textView_quotation.getText().toString(), textView_author.getText().toString()));
        }).start();
    }

    private void showNewRandomQuotation() {
        new ShowNewRandomQuotationThread(this).start();
    }

    public void showNewQuotation(Quotation newQuotation) {
        textView_quotation.setText(newQuotation.getText());
        textView_author.setText(newQuotation.getAuthor());

        refreshMenuItem.setVisible(true);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showAddFavouriteQuotationMenuItem() {
        addMenuItem.setVisible(true);
    }

    public void hideActionBarAndShowProgressBar() {
        refreshMenuItem.setVisible(false);
        addMenuItem.setVisible(false);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

}
