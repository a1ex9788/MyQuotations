package a1ex9788.dadm.myquotations;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RandomQuotationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotations);

        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(String.format(getString(R.string.textView_refreshQuotation), getString(R.string.textView_noUserName)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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