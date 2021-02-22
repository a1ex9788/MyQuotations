package a1ex9788.dadm.myquotations;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import a1ex9788.dadm.myquotations.adapters.FavouriteQuotationsAdapter;
import a1ex9788.dadm.myquotations.databases.QuotationDatabase;
import a1ex9788.dadm.myquotations.databases.QuotationDatabaseAccess;
import a1ex9788.dadm.myquotations.model.Quotation;
import a1ex9788.dadm.myquotations.threads.ShowFavouriteQuotationsThread;

public class FavouriteQuotationsActivity extends AppCompatActivity {

    private FavouriteQuotationsAdapter favouriteQuotationsAdapter;

    private QuotationDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_quotations);

        database = QuotationDatabaseAccess.getDatabase(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_favouriteQuotations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        favouriteQuotationsAdapter = new FavouriteQuotationsAdapter(new ArrayList());
        favouriteQuotationsAdapter.onItemClickListener = position -> {
            showAuthorInfo(favouriteQuotationsAdapter.getQuotation(position).getAuthor());
        };
        favouriteQuotationsAdapter.onItemLongClickListener = position -> {
            showDeleteQuotationDialog(position);
        };
        recyclerView.setAdapter(favouriteQuotationsAdapter);

        new ShowFavouriteQuotationsThread(this).start();
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

    public void showQuotations(List<Quotation> quotations) {
        favouriteQuotationsAdapter.setQuotations(quotations);
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

        List<ResolveInfo> activities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (activities.size() > 0) {
            startActivity(intent);
        }
    }

    private void showDeleteQuotationDialog(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_deleteQuotation);
        alert.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            Quotation quotationToDelete = favouriteQuotationsAdapter.getQuotation(position);
            favouriteQuotationsAdapter.removeQuotation(position);

            if (favouriteQuotationsAdapter.getItemCount() == 0) {
                findViewById(R.id.menu_deleteAllFavouriteQuotations).setVisibility(View.INVISIBLE);
            }

            new Thread(() -> {
                database.deleteQuotation(quotationToDelete);
            }).start();
        });
        alert.setNegativeButton(R.string.dialog_no, null);

        alert.create().show();
    }

    private void showDeleteAllQuotationsDialog(MenuItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.dialog_deleteAllQuotations);
        alert.setPositiveButton(R.string.dialog_yes, (dialog, which) -> {
            item.setVisible(false);
            favouriteQuotationsAdapter.removeAllQuotations();

            new Thread(() -> {
                database.deleteAllQuotations();
            }).start();
        });
        alert.setNegativeButton(R.string.dialog_no, null);

        alert.create().show();
    }

}