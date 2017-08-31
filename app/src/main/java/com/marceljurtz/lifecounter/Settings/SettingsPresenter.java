package com.marceljurtz.lifecounter.Settings;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class SettingsPresenter implements ISettingsPresenter {

    ISettingsView settingsView;
    SharedPreferences preferences;

    public SettingsPresenter(ISettingsView view, SharedPreferences preferences) {
        settingsView = view;
        this.preferences = preferences;
    }

    @Override
    public void onCreate() {
        settingsView.updateColorButtonValue(new Color(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK)));
        settingsView.updateColorButtonValue(new Color(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE)));
        settingsView.updateColorButtonValue(new Color(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN)));
        settingsView.updateColorButtonValue(new Color(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED)));
        settingsView.updateColorButtonValue(new Color(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE)));
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

    @Override
    public void onColorSelectButtonClick(MagicColor color) {
        int savedColor = PreferenceManager.getCustomColor(preferences, color);

        int r = PreferenceManager.getRGB(savedColor)[0];
        int g = PreferenceManager.getRGB(savedColor)[1];
        int b = PreferenceManager.getRGB(savedColor)[2];

        settingsView.loadColorPickerDialog(color, r, g, b);
    }

    @Override
    public void onColorSelectValueUpdate(Color color) {

    }


}
