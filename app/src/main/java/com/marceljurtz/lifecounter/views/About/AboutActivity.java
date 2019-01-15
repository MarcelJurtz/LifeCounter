package com.marceljurtz.lifecounter.views.About;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.marceljurtz.lifecounter.models.AppDetails;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.views.Base.View;

public class AboutActivity extends View implements IAboutView {

    NavigationView navigationView;

    TextView lblProDownload;
    TextView lblProCaption;
    TextView lblProContent;

    TextView lblContactMail;
    TextView lblContactTwitter;

    private TextView lblLicenseColorPicker;
    private TextView lblLicenseAppIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initControls();

        disableMenuItem(navigationView, R.id.nav_energy_save_mode);
        disableMenuItem(navigationView, R.id.nav_about);

        setMenuItemsForPro(navigationView);

        _presenter = new AboutPresenter(this,
                getApplicationContext().getSharedPreferences(PreferenceManager.PREFS, Activity.MODE_PRIVATE));

        _presenter.onCreate();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.nav_dicing:
                        _presenter.onMenuEntryDicingTap();
                        break;
                    case R.id.nav_game_2players:
                        _presenter.onMenuEntryTwoPlayerTap();
                        break;
                    case R.id.nav_game_4players:
                        _presenter.onMenuEntryFourPlayerTap();
                        break;
                    case R.id.nav_settings:
                        _presenter.onMenuEntrySettingsTap();
                        break;
                    case R.id.nav_countermanager:
                        _presenter.onMenuEntryCounterManagerTap();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initControls() {
        navigationView = (NavigationView)findViewById(R.id.navigationViewAbout);

        lblProDownload = findViewById(R.id.lblProDownload);
        lblProCaption = findViewById(R.id.lblProCaption);
        lblProContent = findViewById(R.id.lblProContent);

        lblContactMail = findViewById(R.id.lblContactMail);
        lblContactTwitter = findViewById(R.id.lblContactTwitter);

        lblLicenseColorPicker = findViewById(R.id.lblLicColorPicker);
        lblLicenseAppIntro = findViewById(R.id.lblLicAppIntro);

        if(AppDetails.IS_PRO_MODE) {
            lblProCaption.setVisibility(android.view.View.INVISIBLE);
            lblProContent.setVisibility(android.view.View.INVISIBLE);
            lblProDownload.setVisibility(android.view.View.INVISIBLE);
        } else {
            lblProDownload.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    AboutActivity.super.startBrowserIntent(AppDetails.PRO_URL);
                }
            });
        }

        lblContactMail.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                AboutActivity.super.startBrowserIntent(AppDetails.CONTACT_MAIL);
            }
        });

        lblContactTwitter.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                AboutActivity.super.startBrowserIntent(AppDetails.CONTACT_TWITTER);
            }
        });

        lblLicenseColorPicker.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                AboutActivity.super.startBrowserIntent(getResources().getString(R.string.about_lic_colorpicker_link));
            }
        });

        lblLicenseAppIntro.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                AboutActivity.super.startBrowserIntent(getResources().getString(R.string.about_lic_appintro_link));
            }
        });
    }
}