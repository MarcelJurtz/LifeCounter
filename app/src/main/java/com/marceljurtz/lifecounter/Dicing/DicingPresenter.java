package com.marceljurtz.lifecounter.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class DicingPresenter implements IDicingPresenter {

    private DicingModel model;
    private IDicingView view;
    private SharedPreferences preferences;

    public DicingPresenter(IDicingView view) {
        this.view = view;
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        model = new DicingModel();
        preferences = view.getPreferences();
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

    //endregion
}
