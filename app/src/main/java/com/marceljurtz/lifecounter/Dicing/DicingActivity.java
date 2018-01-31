package com.marceljurtz.lifecounter.Dicing;

import android.app.Activity;
import android.content.Intent;
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

        presenter = new DicingPresenter(this,
                getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE));
        presenter.onCreate();

        rlDicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onScreenTap();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_dicing_useramount_2:
                        presenter.onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_dicing_useramount_4:
                        presenter.onMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_dicing_settings:
                        presenter.onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_dicing_about:
                        presenter.onMenuEntryAboutTap();
                        break;
                    case R.id.nav_countermanager:
                        presenter.onMenuEntryCounterManagerTap();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // Initial dice throw when activity is launching
        presenter.onScreenTap();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void setDicingText(String text) {
        lblDicing.setText(text);
    }

    @Override
    public void loadGameActivity() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void loadSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void loadAboutActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void loadCounterManagerActivity() {
        Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void setBackgroundColor(Color color) {
        rlDicing.setBackgroundColor(color.getIntValue());
    }
}
