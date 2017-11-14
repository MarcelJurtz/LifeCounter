package com.marceljurtz.lifecounter.About;

import android.content.SharedPreferences;

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
    public void OnCreate() {
        preferences = view.GetPreferences();

        if (languageCode.equals("Deutsch")) {
            view.LoadAboutPage("file:///android_asset/about_de.html");
        } else {
            view.LoadAboutPage("file:///android_asset/about_en.html");
        }
    }

    @Override
    public void OnPause() {

    }

    @Override
    public void OnResume() {

    }

    @Override
    public void OnDestroy() {

    }

    //endregion

    //region NavDrawer Handling

    @Override
    public void OnMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        view.LoadGameActivity();
    }

    @Override
    public void OnMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.LoadGameActivity();
    }

    @Override
    public void OnMenuEntryDicingTap() {
        view.LoadDicingActivity();
    }

    @Override
    public void OnMenuEntrySettingsTap() {
        view.LoadSettingsActivity();
    }

    @Override
    public void OnMenuEntryCounterManagerTap() {
        view.LoadCounterManagerActivity();
    }

    //endregion
}
