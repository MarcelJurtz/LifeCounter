package com.marceljurtz.lifecounter.Settings;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;

public class SettingsPresenter implements ISettingsPresenter {

    ISettingsView settingsView;

    public SettingsPresenter(ISettingsView view) {
        settingsView = view;
    }

    @Override
    public void onCreate(IView view) {

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

    @Override
    public void onBackButtonClick() {
        // TODO SaveSettings
        settingsView.loadGameActivity();
    }

    @Override
    public void onCancelButtonClick() {

    }

    @Override
    public void onResetButtonClick() {

    }
}
