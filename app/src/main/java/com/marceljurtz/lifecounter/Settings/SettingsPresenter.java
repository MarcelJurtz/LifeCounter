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
        black = new Color(MagicColor.BLACK, preferences);
        blue = new Color(MagicColor.BLUE, preferences);
        green = new Color(MagicColor.GREEN, preferences);
        red = new Color(MagicColor.RED, preferences);
        white = new Color(MagicColor.WHITE, preferences);

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

        PreferenceManager.SaveColor(preferences, black);
        PreferenceManager.SaveColor(preferences, blue);
        PreferenceManager.SaveColor(preferences, green);
        PreferenceManager.SaveColor(preferences, red);
        PreferenceManager.SaveColor(preferences, white);

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
        int savedColor = PreferenceManager.GetCustomizedColorOrDefault(color, preferences);

        int r = Color.GetRGB(savedColor)[0];
        int g = Color.GetRGB(savedColor)[1];
        int b = Color.GetRGB(savedColor)[2];

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
        PreferenceManager.ResetColors(preferences);

        settingsView.LoadGameActivity();
    }

    @Override
    public void OnResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
