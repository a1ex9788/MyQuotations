package a1ex9788.dadm.myquotations;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void onClickAnyButton(View view) {
        switch (view.getId()) {
            case R.id.button_getQuotations:
                break;
            case R.id.button_favouriteQuotations:
                break;
            case R.id.button_settings:
                break;
            case R.id.button_about:
                break;
        }
    }

}