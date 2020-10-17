package com.marceljurtz.lifecounter.views.Base;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.contracts.INavDrawerInteraction;
import com.marceljurtz.lifecounter.contracts.base.IPresenter;
import com.marceljurtz.lifecounter.contracts.base.IView;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.About.AboutActivity;
import com.marceljurtz.lifecounter.views.Counter.CounterActivity;
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.views.Game.GameActivity;
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity;

import kotlin.NotImplementedError;

public class Presenter implements IPresenter {

    protected SharedPreferences _preferences;
    protected IView _view;
    protected boolean _powerSaveEnabled;

    public Presenter(IView view, SharedPreferences prefs) {
        _view = view;
        _preferences = prefs;
    }

    //region Activity Lifecycle

    public void onCreate() {

    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {

    }

    //endregion

    //region NavDrawer

    public void onMenuEntryTwoPlayerTap() {
        PreferenceManager.INSTANCE.saveDefaultPlayerAmount(_preferences, 2);
        _view.loadActivity(GameActivity.class);
    }

    public void onMenuEntryFourPlayerTap() {
        PreferenceManager.INSTANCE.saveDefaultPlayerAmount(_preferences, 4);
        _view.loadActivity(GameActivity.class);
    }

    public void onMenuEntryDicingTap() {
        _view.loadActivity(DicingActivity.class);
    }

    public void onMenuEntryCounterManagerTap() {
        _view.loadActivity(CounterActivity.class);
    }

    public void onMenuEntrySettingsTap() {
        _view.loadActivity(SettingsActivity.class);
    }

    public void onMenuEntryAboutTap() {
        _view.loadActivity(AboutActivity.class);
    }

    public void onMenuEntryTogglePlayerTap() {
        if(_view instanceof GameActivity){
            GameActivity v = (GameActivity)_view;
            if (v.getPlayerAmount() == 4) {
                // Load 2 Player View
                PreferenceManager.INSTANCE.saveDefaultPlayerAmount(_preferences, 2);
            } else if (v.getPlayerAmount() == 2) {
                // Load 4 Player View
                PreferenceManager.INSTANCE.saveDefaultPlayerAmount(_preferences, 4);
            }
            v.hideNavigationDrawer();
            v.restartActivity();
        }
    }

    public void onMenuEntryEnergySaveTap() {
        if(_view instanceof GameActivity) {
            GameActivity v = (GameActivity)_view;
            _powerSaveEnabled = !_powerSaveEnabled;
            if (_powerSaveEnabled) {
                v.enableEnergySaving(PreferenceManager.INSTANCE.getPowerSave(), PreferenceManager.INSTANCE.getPowerSaveTextcolor());
            } else {
                v.disableEnergySaving(PreferenceManager.INSTANCE.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences), PreferenceManager.INSTANCE.getRegularTextcolor());
            }
            v.setDrawerTextPowerSaving(!_powerSaveEnabled);
        }
    }

    //endregion
}
