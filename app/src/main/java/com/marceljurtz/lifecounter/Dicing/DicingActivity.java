package com.marceljurtz.lifecounter.Dicing;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.About.AboutActivity;
import com.marceljurtz.lifecounter.Counter.CounterActivity;
import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class DicingActivity extends AppCompatActivity implements IDicingView {

    private IDicingPresenter presenter;
    private TextView lblDicing;
    private RelativeLayout rlDicing;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicing);

        lblDicing = (TextView)findViewById(R.id.lblDicing);
        rlDicing = (RelativeLayout)findViewById(R.id.rlDicing);
        navigationView = (NavigationView)findViewById(R.id.navigationViewDicing);

        presenter = new DicingPresenter(this);
        presenter.OnCreate();

        rlDicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnScreenTap();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_dicing_useramount_2:
                        presenter.OnMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_dicing_useramount_4:
                        presenter.OnMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_dicing_settings:
                        presenter.OnMenuEntrySettingsTap();
                        break;
                    case R.id.nav_dicing_about:
                        presenter.OnMenuEntryAboutTap();
                        break;
                    case R.id.nav_countermanager:
                        presenter.OnMenuEntryCounterManagerTap();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // Initial dice throw when activity is launching
        presenter.OnScreenTap();
    }

    @Override
    protected void onDestroy() {
        presenter.OnDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.OnPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.OnResume();
    }

    @Override
    public void SetDicingText(String text) {
        lblDicing.setText(text);
    }

    @Override
    public void LoadGameActivity() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void LoadSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void LoadAboutActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void LoadCounterManagerActivity() {
        Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public SharedPreferences GetPreferences() {
        return getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE);
    }

    @Override
    public void SetBackgroundColor(Color color) {
        rlDicing.setBackgroundColor(color.getIntValue());
    }
}
