package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.Dice;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.Random;

public class DicingPresenter implements IDicingPresenter {

    private Dice model;
    private IDicingView view;
    private SharedPreferences preferences;
    private Random random;

    public DicingPresenter(IDicingView view, SharedPreferences preferences) {
        this.view = view;
        this.preferences = preferences;
        this.random = new Random();
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        model = new Dice();
        int num = random.nextInt(4);
        Color color = new Color(MagicColor.WHITE, preferences);
        switch(num) {
            case 0:
                color = new Color(MagicColor.BLUE, preferences);
                break;
            case 1:
                color = new Color(MagicColor.GREEN, preferences);
                break;
            case 2:
                color = new Color(MagicColor.RED, preferences);
                break;
            case 3:
                break;
        }
        view.setBackgroundColor(color);
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

    @Override
    public void onScreenTap() {
        view.setDicingText(model.throwDice()+"");
    }


    //region NavDrawer Handling

    @Override
    public void onMenuEntrySettingsTap() {
        view.loadSettingsActivity();
    }

    @Override
    public void onMenuEntryAboutTap() {
        view.loadAboutActivity();
    }

    @Override
    public void onMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        view.loadGameActivity();
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        view.loadGameActivity();
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        view.loadCounterManagerActivity();
    }

    //endregion
}
