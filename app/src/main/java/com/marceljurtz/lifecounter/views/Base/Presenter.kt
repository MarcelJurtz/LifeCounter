package com.marceljurtz.lifecounter.views.Base

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.contracts.INavDrawerInteraction
import com.marceljurtz.lifecounter.contracts.base.IPresenter
import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.About.AboutActivity
import com.marceljurtz.lifecounter.views.Counter.CounterActivity
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity
import com.marceljurtz.lifecounter.views.Game.GameActivity
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity

open class Presenter(protected var _view: IView, protected var _preferences: SharedPreferences) : IPresenter {
    protected var _powerSaveEnabled: Boolean = false

    //region Activity Lifecycle

    override fun onCreate() {

    }

    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun onDestroy() {

    }

    //endregion

    //region NavDrawer

    override fun onMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 2)
        _view.loadActivity(GameActivity::class.java)
    }

    override fun onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 4)
        _view.loadActivity(GameActivity::class.java)
    }

    override fun onMenuEntryDicingTap() {
        _view.loadActivity(DicingActivity::class.java)
    }

    override fun onMenuEntryCounterManagerTap() {
        _view.loadActivity(CounterActivity::class.java)
    }

    override fun onMenuEntrySettingsTap() {
        _view.loadActivity(SettingsActivity::class.java)
    }

    override fun onMenuEntryAboutTap() {
        _view.loadActivity(AboutActivity::class.java)
    }

    override fun onMenuEntryTogglePlayerTap() {
        if (_view is GameActivity) {
            val v = _view as GameActivity
            if (v.playerAmount == 4) {
                // Load 2 Player View
                PreferenceManager.saveDefaultPlayerAmount(_preferences, 2)
            } else if (v.playerAmount == 2) {
                // Load 4 Player View
                PreferenceManager.saveDefaultPlayerAmount(_preferences, 4)
            }
            v.hideNavigationDrawer()
            v.restartActivity()
        }
    }

    override fun onMenuEntryEnergySaveTap() {
        if (_view is GameActivity) {
            val v = _view as GameActivity
            _powerSaveEnabled = !_powerSaveEnabled
            if (_powerSaveEnabled) {
                v.enableEnergySaving(PreferenceManager.powerSave, PreferenceManager.powerSaveTextcolor)
            } else {
                v.disableEnergySaving(PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences), PreferenceManager.regularTextcolor)
            }
            v.setDrawerTextPowerSaving(!_powerSaveEnabled)
        }
    }

    //endregion
}
