package com.marceljurtz.lifecounter.views.Dicing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;

import androidx.appcompat.app.AppCompatActivity;

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
                    case R.id.nav_game_2players:
                        presenter.onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        presenter.onMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_settings:
                        presenter.onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_about:
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
    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        finish();
        startActivity(intent);
    }

    @Override
    public void setBackgroundColor(Color color) {
        rlDicing.setBackgroundColor(color.getIntValue());
    }
}
