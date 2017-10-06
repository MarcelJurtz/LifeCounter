package com.marceljurtz.lifecounter.Dicing;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Game.GameActivity;
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

        presenter = new DicingPresenter(this);
        presenter.onCreate();

        lblDicing = (TextView)findViewById(R.id.lblDicing);
        rlDicing = (RelativeLayout)findViewById(R.id.rlDicing);
        rlDicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onScreenTap();
            }
        });
        navigationView = (NavigationView)findViewById(R.id.navigationViewDicing);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_dicing_useramount_2:
                        presenter.onMenuEntry2PlayerTap();
                        break;
                    case R.id.nav_dicing_useramount_4:
                        presenter.onMenuEntry4PlayerTap();
                        break;
                    case R.id.nav_dicing_settings:
                        presenter.onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_dicing_about:
                        presenter.onMenuEntryAboutTap();
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
    public void start2PlayerGame() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void start4PlayerGame() {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void startSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        finish();
        startActivity(intent);
    }
}
