package a1ex9788.dadm.myquotations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Array;
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

        FavouriteQuotationsAdapter favouriteQuotationsAdapter = new FavouriteQuotationsAdapter(getMockQuotations());
        RecyclerView recyclerView = findViewById(R.id.recyclerView_favouriteQuotations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(favouriteQuotationsAdapter);
    }

    public void onClickAuthorInfo(View view) {
        String authorName = "Santiago_Segura";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/" + authorName));

        // Check that there exists an Activity able to manage that Intent
        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    public ArrayList<Quotation> getMockQuotations() {
        return new ArrayList<>(Arrays.asList(
            new Quotation("Quotation 1", "Author 1"),
            new Quotation("Quotation 2", "Author 2"),
            new Quotation("Quotation 3", "Author 3"),
            new Quotation("Quotation 4", "Author 4"),
            new Quotation("Quotation 5", "Author 5"),
            new Quotation("Quotation 6", "Author 6"),
            new Quotation("Quotation 7", "Author 7"),
            new Quotation("Quotation 8", "Author 8"),
            new Quotation("Quotation 9", "Author 9"),
            new Quotation("Quotation 10", "Author 10")
        ));
    }
}