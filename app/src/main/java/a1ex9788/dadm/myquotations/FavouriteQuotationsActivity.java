package a1ex9788.dadm.myquotations;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a1ex9788.dadm.myquotations.adapters.FavouriteQuotationsAdapter;
import a1ex9788.dadm.myquotations.model.Quotation;

public class FavouriteQuotationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_quotations);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_favouriteQuotations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new FavouriteQuotationsAdapter(getMockQuotations(), position -> {
            showAuthorInfo(FavouriteQuotationsAdapter.getQuotation(position).getQuoteAuthor());
        }));
    }

    public void showAuthorInfo(String authorName) {
        authorName = URLEncoder.encode(authorName);

        if (authorName == null || authorName.isEmpty()) {
            Toast.makeText(this, R.string.toast_noAuthor, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.url_wikipedia) + authorName));

        // Check that there exists an Activity able to manage that Intent
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    public ArrayList<Quotation> getMockQuotations() {
        return new ArrayList<>(Arrays.asList(
                new Quotation("Quotation 1", "Berta Escobar"),
                new Quotation("Quotation 2", ""),
                new Quotation("Quotation 3", "Ignatius Farray"),
                new Quotation("Quotation 4", "Berto Romero"),
                new Quotation("Quotation 5", "Enrique Pastor"),
                new Quotation("Quotation 6", "Santiago Segura"),
                new Quotation("Quotation 7", "Juan del Val"),
                new Quotation("Quotation 8", "Nuria Roca"),
                new Quotation("Quotation 9", "Pablo Motos"),
                new Quotation("Quotation 10", "David Broncano")
        ));
    }

}