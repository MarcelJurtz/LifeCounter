package com.marceljurtz.lifecounter.About;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.marceljurtz.lifecounter.Counter.CounterActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

import java.util.Locale;

public class AboutActivity extends AppCompatActivity implements IAboutView {

    IAboutPresenter presenter;
    WebView mainWebView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        mainWebView = (WebView)findViewById(R.id.wvAbout);

        navigationView = (NavigationView)findViewById(R.id.navigationViewAbout);

        presenter = new AboutPresenter(this,
                getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE),
                Locale.getDefault().getDisplayLanguage());
        presenter.OnCreate();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_about_dicing:
                        presenter.OnMenuEntryDicingTap();
                        break;
                    case R.id.nav_about_useramount_2:
                        presenter.OnMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_about_useramount_4:
                        presenter.OnMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_about_settings:
                        presenter.OnMenuEntrySettingsTap();
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
    public void LoadAboutPage(String url) {
        mainWebView.loadUrl(url);
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
    public void LoadDicingActivity() {
        Intent intent = new Intent(getApplicationContext(), DicingActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void LoadCounterManagerActivity() {
        Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
        finish();
        startActivity(intent);
    }
}