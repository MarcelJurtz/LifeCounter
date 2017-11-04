package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IAboutNavDrawerInteraction;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class AboutPresenter implements IAboutPresenter {

    String languageCode;
    IAboutView view;
    private SharedPreferences preferences;

    public AboutPresenter(IAboutView view, String languageCode) {
        this.view = view;
        this.languageCode = languageCode;
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        preferences = view.getPreferences();

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
        view.startGameActivity();
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.startGameActivity();
    }

    @Override
    public void onMenuEntryDicingTap() {
        view.startDicingActivity();
    }

    @Override
    public void onMenuEntrySettingsTap() {
        view.startSettingsActivity();
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        view.startCounterManagerActivity();
    }

    //endregion
}
