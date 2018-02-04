package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Counter.CounterActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class AboutPresenter implements IAboutPresenter {

    String languageCode;
    IAboutView view;
    private SharedPreferences preferences;

    public AboutPresenter(IAboutView view, SharedPreferences preferences, String languageCode) {
        this.view = view;
        this.preferences = preferences;
        this.languageCode = languageCode;
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        if (languageCode.equals("Deutsch")) {
            view.loadAboutPage("file:///android_asset/about_de.html");
        } else {
            view.loadAboutPage("file:///android_asset/about_en.html");
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    //endregion

    //region NavDrawer Handling

    @Override
    public void onMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        view.loadActivity(GameActivity.class);
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.loadActivity(GameActivity.class);
    }

    @Override
    public void onMenuEntryDicingTap() {
        view.loadActivity(DicingActivity.class);
    }

    @Override
    public void onMenuEntrySettingsTap() {
        view.loadActivity(SettingsActivity.class);
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        view.loadActivity(CounterActivity.class);
    }

    //endregion
}
