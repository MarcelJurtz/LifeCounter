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
    public void OnCreate() {
        model = new DicingModel();
        preferences = view.GetPreferences();
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
        view.SetBackgroundColor(color);
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

    @Override
    public void OnScreenTap() {
        view.SetDicingText(model.ThrowDice()+"");
    }


    //region NavDrawer Handling

    @Override
    public void OnMenuEntrySettingsTap() {
        view.LoadSettingsActivity();
    }

    @Override
    public void OnMenuEntryAboutTap() {
        view.LoadAboutActivity();
    }

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
    public void OnMenuEntryCounterManagerTap() {
        view.LoadCounterManagerActivity();
    }

    //endregion
}
