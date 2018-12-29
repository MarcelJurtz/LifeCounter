package com.marceljurtz.lifecounter.views.Dicing;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.views.About.AboutActivity;
import com.marceljurtz.lifecounter.views.Base.Presenter;
import com.marceljurtz.lifecounter.views.Counter.CounterActivity;
import com.marceljurtz.lifecounter.views.Game.GameActivity;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.Dice;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity;

import java.util.Random;

public class DicingPresenter extends Presenter implements IDicingPresenter {

    private Dice _dice;
    private Random _random;
    private IDicingView _view;

    public DicingPresenter(IDicingView view, SharedPreferences preferences) {
        super(view, preferences);
        _view = view;
        this._random = new Random();
    }

    //region Activity Lifecycle

    @Override
    public void onCreate() {
        _dice = new Dice();
        int num = _random.nextInt(4);
        Color color = new Color(MagicColorEnum.WHITE, _preferences);
        switch(num) {
            case 0:
                color = new Color(MagicColorEnum.BLUE, _preferences);
                break;
            case 1:
                color = new Color(MagicColorEnum.GREEN, _preferences);
                break;
            case 2:
                color = new Color(MagicColorEnum.RED, _preferences);
                break;
            case 3:
                break;
        }
        _view.setBackgroundColor(color);
    }

    //endregion

    @Override
    public void onScreenTap() {
        _view.setDicingText(_dice.throwDice()+"");
    }


    //region NavDrawer Handling

    @Override
    public void onMenuEntrySettingsTap() {
        _view.loadActivity(SettingsActivity.class);
    }

    @Override
    public void onMenuEntryAboutTap() {
        _view.loadActivity(AboutActivity.class);
    }

    @Override
    public void onMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 2);
        _view.loadActivity(GameActivity.class);
    }

    @Override
    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 4);
        _view.loadActivity(GameActivity.class);
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        _view.loadActivity(CounterActivity.class);
    }

    //endregion
}
