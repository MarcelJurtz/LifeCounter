package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.PreferenceManager;

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
            view.LoadAboutPage("file:///android_asset/about_de.html");
        } else {
            view.LoadAboutPage("file:///android_asset/about_en.html");
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
        view.LoadGameActivity();
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.LoadGameActivity();
    }

    @Override
    public void onMenuEntryDicingTap() {
        view.LoadDicingActivity();
    }

    @Override
    public void onMenuEntrySettingsTap() {
        view.LoadSettingsActivity();
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        view.LoadCounterManagerActivity();
    }

    //endregion
}
