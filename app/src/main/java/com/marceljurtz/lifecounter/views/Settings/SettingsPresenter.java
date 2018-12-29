package com.marceljurtz.lifecounter.views.Settings;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.views.About.AboutActivity;
import com.marceljurtz.lifecounter.views.Base.Presenter;
import com.marceljurtz.lifecounter.views.Counter.CounterActivity;
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.views.Game.GameActivity;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.Intro.IntroActivity;

public class SettingsPresenter extends Presenter implements ISettingsPresenter {

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
        super(view, preferences);
        settingsView = view;
        this.preferences = preferences;
    }

    //region Activity LifeCycle Functions

    @Override
    public void onCreate() {
        black = new Color(MagicColorEnum.BLACK, preferences);
        blue = new Color(MagicColorEnum.BLUE, preferences);
        green = new Color(MagicColorEnum.GREEN, preferences);
        red = new Color(MagicColorEnum.RED, preferences);
        white = new Color(MagicColorEnum.WHITE, preferences);

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

        settingsView.returnToPrevActivity();
    }

    @Override
    public void onCancelButtonClick() {
        // Discard Settings
        settingsView.returnToPrevActivity();
    }

    //endregion

    //region Update color values

    @Override
    public void onColorSelectButtonClick(MagicColorEnum color) {
        int savedColor = PreferenceManager.getCustomizedColorOrDefault(color, preferences);

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

    @Override
    public void onShowAppIntroClick() {
        settingsView.loadActivity(IntroActivity.class);
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

        settingsView.returnToPrevActivity();
    }

    @Override
    public void onResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
