package com.marceljurtz.lifecounter.views.Dicing

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.views.About.AboutActivity
import com.marceljurtz.lifecounter.views.Base.Presenter
import com.marceljurtz.lifecounter.views.Counter.CounterActivity
import com.marceljurtz.lifecounter.views.Game.GameActivity
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.Dice
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity

import java.util.Random

class DicingPresenter(_view: IDicingView, preferences: SharedPreferences) : Presenter(_view, preferences), IDicingPresenter {

    private var _dice: Dice? = null
    private val _random: Random

    private val _dicingView: IDicingView = _view;

    init {
        this._random = Random()
    }

    //region Activity Lifecycle

    override fun onCreate() {
        _dice = Dice()
        val num = _random.nextInt(4)
        var color = Color(MagicColorEnum.WHITE, _preferences)
        when (num) {
            0 -> color = Color(MagicColorEnum.BLUE, _preferences)
            1 -> color = Color(MagicColorEnum.GREEN, _preferences)
            2 -> color = Color(MagicColorEnum.RED, _preferences)
            3 -> {
            }
        }
        _dicingView.setBackgroundColor(color)
    }

    //endregion

    override fun onScreenTap() {
        _dicingView.setDicingText(_dice!!.throwDice().toString() + "")
    }


    //region NavDrawer Handling

    override fun onMenuEntrySettingsTap() {
        _view.loadActivity(SettingsActivity::class.java)
    }

    override fun onMenuEntryAboutTap() {
        _view.loadActivity(AboutActivity::class.java)
    }

    override fun onMenuEntryTwoPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 2)
        _view.loadActivity(GameActivity::class.java)
    }

    override fun onMenuEntryFourPlayerTap() {
        PreferenceManager.saveDefaultPlayerAmount(_preferences, 4)
        _view.loadActivity(GameActivity::class.java)
    }

    override fun onMenuEntryCounterManagerTap() {
        _view.loadActivity(CounterActivity::class.java)
    }

    //endregion
}
