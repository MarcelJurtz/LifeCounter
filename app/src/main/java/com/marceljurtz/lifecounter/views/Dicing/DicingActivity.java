package com.marceljurtz.lifecounter.views.Dicing;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;

public class DicingActivity extends com.marceljurtz.lifecounter.views.Base.View implements IDicingView {

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

        disableMenuItem(navigationView, R.id.nav_dicing);
        disableMenuItem(navigationView, R.id.nav_energy_save_mode);

        set_presenter(new DicingPresenter(this, getApplicationContext().getSharedPreferences(PreferenceManager.INSTANCE.getPREFS(), Activity.MODE_PRIVATE)));
        get_presenter().onCreate();

        rlDicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IDicingPresenter) get_presenter()).onScreenTap();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_game_2players:
                        get_presenter().onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        get_presenter().onMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_settings:
                        get_presenter().onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_about:
                        get_presenter().onMenuEntryAboutTap();
                        break;
                    case R.id.nav_countermanager:
                        get_presenter().onMenuEntryCounterManagerTap();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        // Initial dice throw when activity is launching
        ((IDicingPresenter) get_presenter()).onScreenTap();
    }

    @Override
    public void setDicingText(String text) {
        lblDicing.setText(text);
    }

    @Override
    public void setBackgroundColor(Color color) {
        rlDicing.setBackgroundColor(color.getIntValue());
    }
}
