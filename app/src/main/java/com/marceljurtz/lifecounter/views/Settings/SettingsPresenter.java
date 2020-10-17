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

        lifepoints = PreferenceManager.INSTANCE.getDefaultLifepoints(preferences);
        settingsView.setLifepoints(String.format("%02d", lifepoints));

        longClickPoints = PreferenceManager.INSTANCE.getLongclickPoints(preferences);
        settingsView.setLongClickPoints(String.format("%02d", longClickPoints));

        settingsView.setKeepScreenOnCheckbox(PreferenceManager.INSTANCE.getScreenTimeoutDisabled(preferences));
        settingsView.setConfirmGameResetCheckbox(PreferenceManager.INSTANCE.getConfirmGameReset(preferences));
    }

    //endregion

    //region Back & Cancel Button

    @Override
    public void onBackButtonClick() {

        PreferenceManager.INSTANCE.saveColor(preferences, black);
        PreferenceManager.INSTANCE.saveColor(preferences, blue);
        PreferenceManager.INSTANCE.saveColor(preferences, green);
        PreferenceManager.INSTANCE.saveColor(preferences, red);
        PreferenceManager.INSTANCE.saveColor(preferences, white);

        lifepoints = Integer.parseInt(settingsView.getLifepoints());
        longClickPoints = Integer.parseInt(settingsView.getLongClickPoints());

        PreferenceManager.INSTANCE.saveDefaultLifepoints(preferences, lifepoints);
        PreferenceManager.INSTANCE.saveDefaultLongClickPoints(preferences, longClickPoints);

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
        int savedColor = PreferenceManager.INSTANCE.getCustomizedColorOrDefault(color, preferences);

        int r = Color.Companion.getRGB(savedColor)[0];
        int g = Color.Companion.getRGB(savedColor)[1];
        int b = Color.Companion.getRGB(savedColor)[2];

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
        PreferenceManager.INSTANCE.saveScreenTimeoutDisabled(preferences, checked);
    }

    @Override
    public void onShowAppIntroClick() {
        settingsView.loadActivity(IntroActivity.class);
    }

    @Override
    public void onConfirmGameResetCheckboxClick(boolean checked) {
        PreferenceManager.INSTANCE.saveConfirmGameReset(preferences, checked);
    }

    //region Reset Button Click

    @Override
    public void onResetButtonClick() {
        settingsView.loadResetConfirmationDialog();
    }

    @Override
    public void onResetButtonConfirm() {
        // Reset all Settings
        PreferenceManager.INSTANCE.resetLongClickPoints(preferences);
        PreferenceManager.INSTANCE.resetLifepoints(preferences);
        PreferenceManager.INSTANCE.resetColors(preferences);

        settingsView.returnToPrevActivity();
    }

    @Override
    public void onResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
