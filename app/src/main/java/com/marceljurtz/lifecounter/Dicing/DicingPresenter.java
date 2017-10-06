package com.marceljurtz.lifecounter.Dicing;

import android.content.Intent;

import com.marceljurtz.lifecounter.Game.GameActivity;
import com.marceljurtz.lifecounter.Game.IGameView;

public class DicingPresenter implements IDicingPresenter {

    private DicingModel model;
    private IDicingView view;

    public DicingPresenter(IDicingView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        model = new DicingModel();
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
    public void onScreenTap() {
        view.setDicingText(model.ThrowDice()+"");
    }

    @Override
    public void onMenuEntry2PlayerTap() {
        view.start2PlayerGame();
    }

    @Override
    public void onMenuEntry4PlayerTap() {
        view.start4PlayerGame();
    }

    @Override
    public void onMenuEntrySettingsTap() {
        view.startSettingsActivity();
    }

    @Override
    public void onMenuEntryAboutTap() {

    }
}
