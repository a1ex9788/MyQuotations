package a1ex9788.dadm.myquotations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void onClickAnyButton(View view) {
        Class<?> activityClass = null;

        switch (view.getId()) {
            case R.id.button_getQuotations:
                activityClass = RandomQuotationsActivity.class;
                break;
            case R.id.button_favouriteQuotations:
                activityClass = FavouriteQuotationsActivity.class;
                break;
            case R.id.button_settings:
                activityClass = SettingsActivity.class;
                break;
            case R.id.button_about:
                activityClass = AboutActivity.class;
                break;
        }

        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}