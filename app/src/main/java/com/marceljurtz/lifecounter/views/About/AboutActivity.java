package com.marceljurtz.lifecounter.views.About;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;

import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity implements IAboutView {

    IAboutPresenter presenter;
    WebView mainWebView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //mainWebView = (WebView)findViewById(R.id.wvAbout);

        navigationView = (NavigationView)findViewById(R.id.navigationViewAbout);

        presenter = new AboutPresenter(this,
                getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE),
                Locale.getDefault().getDisplayLanguage());
        presenter.onCreate();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_dicing:
                        presenter.onMenuEntryDicingTap();
                        break;
                    case R.id.nav_game_2players:
                        presenter.onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        presenter.onMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_settings:
                        presenter.onMenuEntrySettingsTap();
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
        /*
        Element adsElement = new Element();
        adsElement.setTitle("Advertise with us");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.icon_small)
                .addItem(new Element().setTitle("Version 6.2"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("feedback@mjurtz.com")
                .addWebsite("http://www.mjurtz.com/")
                .addTwitter("@MarcelJurtz")
                .addPlayStore("https://play.google.com/store/apps/developer?id=Marcel+Jurtz")
                .addGitHub("MarcelJurtz")
                .addItem(getCopyRightsElement())
                .addItem(getChangeLog())
                .addItem(getLibsElement())
                .create();

        setContentView(aboutPage);*/
    }
/*
    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        copyRightsElement.setTitle(getResources().getString(R.string.about_copyright_header));
        copyRightsElement.setValue(getResources().getString(R.string.about_copyright_content));
        return copyRightsElement;
    }

    Element getLibsElement() {
        Element licsElement = new Element();
        licsElement.setTitle(getResources().getString(R.string.about_lics_header));
        licsElement.setValue(getResources().getString(R.string.about_lics_content));
        return licsElement;
    }

    Element getChangeLog() {
        Element chElement = new Element();
        chElement.setTitle(getResources().getString(R.string.about_changelog_header));
        chElement.setValue(getResources().getString(R.string.about_changelog_content));
        return chElement;
    }
*/
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
    public void loadAboutPage(String url) {
        //mainWebView.loadUrl(url);
    }

    @Override
    public void loadActivity(Class c) {
        Intent intent = new Intent(getApplicationContext(), c);
        finish();
        startActivity(intent);
    }
}