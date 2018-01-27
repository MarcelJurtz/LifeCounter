package com.marceljurtz.lifecounter.Settings;

import android.content.SharedPreferences;

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
    public void OnCreate() {
        black = new Color(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK));
        blue = new Color(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE));
        green = new Color(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN));
        red = new Color(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED));
        white = new Color(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE));

        settingsView.UpdateColorButtonValue(black);
        settingsView.UpdateColorButtonValue(blue);
        settingsView.UpdateColorButtonValue(green);
        settingsView.UpdateColorButtonValue(red);
        settingsView.UpdateColorButtonValue(white);

        lifepoints = PreferenceManager.getDefaultLifepoints(preferences);
        settingsView.SetLifepoints(String.format("%02d", lifepoints));

        longClickPoints = PreferenceManager.getLongclickPoints(preferences);
        settingsView.SetLongClickPoints(String.format("%02d", longClickPoints));

        settingsView.SetKeepScreenOnCheckbox(PreferenceManager.getScreenTimeoutDisabled(preferences));
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

    //region Back & Cancel Button

    @Override
    public void OnBackButtonClick() {

        PreferenceManager.saveColor(preferences, black);
        PreferenceManager.saveColor(preferences, blue);
        PreferenceManager.saveColor(preferences, green);
        PreferenceManager.saveColor(preferences, red);
        PreferenceManager.saveColor(preferences, white);

        lifepoints = Integer.parseInt(settingsView.GetLifepoints());
        longClickPoints = Integer.parseInt(settingsView.GetLongClickPoints());

        PreferenceManager.saveDefaultLifepoints(preferences, lifepoints);
        PreferenceManager.saveDefaultLongClickPoints(preferences, longClickPoints);

        settingsView.LoadGameActivity();
    }

    @Override
    public void OnCancelButtonClick() {
        // Discard Settings
        settingsView.LoadGameActivity();
    }

    //endregion

    //region Update color values

    @Override
    public void OnColorSelectButtonClick(MagicColor color) {
        int savedColor = PreferenceManager.getCustomColor(preferences, color);

        int r = Color.getRGB(savedColor)[0];
        int g = Color.getRGB(savedColor)[1];
        int b = Color.getRGB(savedColor)[2];

        settingsView.LoadColorPickerDialog(color, r, g, b);
    }

    @Override
    public void OnColorSelectValueUpdate(Color color) {
        switch(color.GetBasecolor()) {
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
        settingsView.UpdateColorButtonValue(color);
    }

    //endregion

    @Override
    public void OnKeepScreenOnCheckboxClick(boolean checked) {
        PreferenceManager.saveScreenTimeoutDisabled(preferences, checked);
    }


    //region Reset Button Click

    @Override
    public void OnResetButtonClick() {
        settingsView.LoadResetConfirmationDialog();
    }

    @Override
    public void OnResetButtonConfirm() {
        // Reset all Settings
        PreferenceManager.resetLongClickPoints(preferences);
        PreferenceManager.resetLifepoints(preferences);
        PreferenceManager.resetColors(preferences);

        settingsView.LoadGameActivity();
    }

    @Override
    public void OnResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
