package a1ex9788.dadm.myquotations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RandomQuotationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_quotations);

        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(String.format(getString(R.string.textView_refreshQuotation), getString(R.string.textView_noUserName)));
    }

    public void onClickRefreshButton(View view) {
        TextView textView_quotation = findViewById(R.id.textView_quotation);
        textView_quotation.setText(R.string.textView_sampleQuotation);

        TextView textView_author = findViewById(R.id.textView_author);
        textView_author.setText(R.string.textView_sampleActor);
    }
}