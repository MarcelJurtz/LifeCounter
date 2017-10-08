package com.marceljurtz.lifecounter.Settings;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.BaseInterface.IView;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class SettingsPresenter implements ISettingsPresenter {

    ISettingsView settingsView;
    SharedPreferences preferences;

    Color black;
    Color blue;
    Color green;
    Color red;
    Color white;

    private int lifepoints;
    private int longClickPoints;

    public SettingsPresenter(ISettingsView view, SharedPreferences preferences) {
        settingsView = view;
        this.preferences = preferences;
    }

    //region Activity LifeCycle Functions

    @Override
    public void onCreate() {
        black = new Color(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK));
        blue = new Color(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE));
        green = new Color(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN));
        red = new Color(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED));
        white = new Color(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE));

        settingsView.updateColorButtonValue(black);
        settingsView.updateColorButtonValue(blue);
        settingsView.updateColorButtonValue(green);
        settingsView.updateColorButtonValue(red);
        settingsView.updateColorButtonValue(white);

        lifepoints = PreferenceManager.getDefaultLifepoints(preferences);
        settingsView.setLifepoints(String.format("%02d", lifepoints));

        longClickPoints = PreferenceManager.getLongclickPoints(preferences);
        settingsView.setLongClickPoints(String.format("%02d", longClickPoints));

        settingsView.setKeepScreenOnCheckbox(PreferenceManager.getScreenTimeoutDisabled(preferences));
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

    //region Back & Cancel Button

    @Override
    public void onBackButtonClick() {

        PreferenceManager.saveColor(preferences, black);
        PreferenceManager.saveColor(preferences, blue);
        PreferenceManager.saveColor(preferences, green);
        PreferenceManager.saveColor(preferences, red);
        PreferenceManager.saveColor(preferences, white);

        lifepoints = Integer.parseInt(settingsView.getLifepoints());
        longClickPoints = Integer.parseInt(settingsView.getLongClickPoints());

        PreferenceManager.saveDefaultLifepoints(preferences, lifepoints);
        PreferenceManager.saveDefaultLongClickPoints(preferences, longClickPoints);

        settingsView.loadGameActivity();
    }

    @Override
    public void onCancelButtonClick() {
        // Discard Settings
        settingsView.loadGameActivity();
    }

    //endregion

    //region Update color values

    @Override
    public void onColorSelectButtonClick(MagicColor color) {
        int savedColor = PreferenceManager.getCustomColor(preferences, color);

        int r = Color.getRGB(savedColor)[0];
        int g = Color.getRGB(savedColor)[1];
        int b = Color.getRGB(savedColor)[2];

        settingsView.loadColorPickerDialog(color, r, g, b);
    }

    @Override
    public void onColorSelectValueUpdate(Color color) {
        switch(color.getBasecolor()) {
            case BLUE:
                blue = color;
                break;
            case GREEN:
                green = color;
                break;
            case RED:
                red = color;
                break;
            case WHITE:
                white = color;
                break;
            default:
                black = color;
        }
        settingsView.updateColorButtonValue(color);
    }

    //endregion

    @Override
    public void onKeepScreenOnCheckboxClick(boolean checked) {
        PreferenceManager.saveScreenTimeoutDisabled(preferences, checked);
    }


    //region Reset Button Click

    @Override
    public void onResetButtonClick() {
        settingsView.loadResetConfirmationDialog();
    }

    @Override
    public void onResetButtonConfirm() {
        // Reset all Settings
        PreferenceManager.resetLongClickPoints(preferences);
        PreferenceManager.resetLifepoints(preferences);
        PreferenceManager.resetColors(preferences);

        settingsView.loadGameActivity();
    }

    @Override
    public void onResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
