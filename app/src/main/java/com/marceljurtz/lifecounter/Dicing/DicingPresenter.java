package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.Random;

public class DicingPresenter implements IDicingPresenter {

    private DicingModel model;
    private IDicingView view;
    private SharedPreferences preferences;
    private Random random;

    public DicingPresenter(IDicingView view) {
        this.view = view;
        this.random = new Random();
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        model = new DicingModel();
        preferences = view.getPreferences();
        int num = random.nextInt(4);
        Color color = new Color(MagicColor.WHITE);
        switch(num) {
            case 0:
                color = new Color(MagicColor.BLUE);
                break;
            case 1:
                color = new Color(MagicColor.GREEN);
                break;
            case 2:
                color = new Color(MagicColor.RED);
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
        view.setDicingText(model.ThrowDice()+"");
    }


    //region NavDrawer Handling

    @Override
    public void onMenuEntrySettingsTap() {
        view.startSettingsActivity();
    }

    @Override
    public void onMenuEntryAboutTap() {
        view.startAboutActivity();
    }

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
    public void onMenuEntryCounterManagerTap() {
        view.startCounterManagerActivity();
    }

    //endregion
}
