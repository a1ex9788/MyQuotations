package a1ex9788.dadm.myquotations;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

    private FavouriteQuotationsAdapter favouriteQuotationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_quotations);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_favouriteQuotations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        favouriteQuotationsAdapter = new FavouriteQuotationsAdapter(getMockQuotations());
        favouriteQuotationsAdapter.onItemClickListener = position -> {
            showAuthorInfo(favouriteQuotationsAdapter.getQuotation(position).getQuoteAuthor());
        };
        favouriteQuotationsAdapter.onItemLongClickListener = position -> {
            showDeleteQuotationDialog(position);
        };
        recyclerView.setAdapter(favouriteQuotationsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_quotations, menu);
        if (favouriteQuotationsAdapter.getItemCount() == 0) {
            menu.findItem(R.id.menu_deleteAllFavouriteQuotations).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_deleteAllFavouriteQuotations:
                showDeleteAllQuotationsDialog(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAuthorInfo(String authorName) {
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

    private void showDeleteQuotationDialog(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_deleteQuotation);
        alert.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            favouriteQuotationsAdapter.removeQuotation(position);
        });
        alert.setNegativeButton(R.string.dialog_no, null);

        alert.create().show();
    }

    private void showDeleteAllQuotationsDialog(MenuItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_deleteAllQuotations);
        alert.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            favouriteQuotationsAdapter.removeAllQuotations();
            item.setVisible(false);
        });
        alert.setNegativeButton(R.string.dialog_no, null);

        alert.create().show();
    }

    private ArrayList<Quotation> getMockQuotations() {
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