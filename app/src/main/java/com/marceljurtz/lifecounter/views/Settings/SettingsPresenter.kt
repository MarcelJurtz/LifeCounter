package com.marceljurtz.lifecounter.views.Settings

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.views.About.AboutActivity
import com.marceljurtz.lifecounter.views.Base.Presenter
import com.marceljurtz.lifecounter.views.Counter.CounterActivity
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity
import com.marceljurtz.lifecounter.views.Game.GameActivity
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.Intro.IntroActivity

class SettingsPresenter(internal var settingsView: ISettingsView, internal var preferences: SharedPreferences) : Presenter(settingsView, preferences), ISettingsPresenter {

    internal var black: Color? = null
    internal var blue: Color? = null
    internal var green: Color? = null
    internal var red: Color? = null
    internal var white: Color? = null

    private var lifepoints: Int = 0
    private var longClickPoints: Int = 0

    //region Activity LifeCycle Functions

    override fun onCreate() {
        black = Color(MagicColorEnum.BLACK, preferences)
        blue = Color(MagicColorEnum.BLUE, preferences)
        green = Color(MagicColorEnum.GREEN, preferences)
        red = Color(MagicColorEnum.RED, preferences)
        white = Color(MagicColorEnum.WHITE, preferences)

        settingsView.updateColorButtonValue(black!!)
        settingsView.updateColorButtonValue(blue!!)
        settingsView.updateColorButtonValue(green!!)
        settingsView.updateColorButtonValue(red!!)
        settingsView.updateColorButtonValue(white!!)

        lifepoints = PreferenceManager.getDefaultLifepoints(preferences)
        settingsView.lifepoints = String.format("%02d", lifepoints)

        longClickPoints = PreferenceManager.getLongclickPoints(preferences)
        settingsView.longClickPoints = String.format("%02d", longClickPoints)

        settingsView.setKeepScreenOnCheckbox(PreferenceManager.getScreenTimeoutDisabled(preferences))
        settingsView.setConfirmGameResetCheckbox(PreferenceManager.getConfirmGameReset(preferences))
    }

    //endregion

    //region Back & Cancel Button

    override fun onBackButtonClick() {

        PreferenceManager.saveColor(preferences, black!!)
        PreferenceManager.saveColor(preferences, blue!!)
        PreferenceManager.saveColor(preferences, green!!)
        PreferenceManager.saveColor(preferences, red!!)
        PreferenceManager.saveColor(preferences, white!!)

        lifepoints = Integer.parseInt(settingsView.lifepoints)
        longClickPoints = Integer.parseInt(settingsView.longClickPoints)

        PreferenceManager.saveDefaultLifepoints(preferences, lifepoints)
        PreferenceManager.saveDefaultLongClickPoints(preferences, longClickPoints)

        settingsView.returnToPrevActivity()
    }

    override fun onCancelButtonClick() {
        // Discard Settings
        settingsView.returnToPrevActivity()
    }

    //endregion

    //region Update color values

    override fun onColorSelectButtonClick(color: MagicColorEnum) {
        val savedColor = PreferenceManager.getCustomizedColorOrDefault(color, preferences)

        val r = Color.getRGB(savedColor)[0]
        val g = Color.getRGB(savedColor)[1]
        val b = Color.getRGB(savedColor)[2]

        settingsView.loadColorPickerDialog(color, r, g, b)
    }

    override fun onColorSelectValueUpdate(color: Color) {
        when (color.basecolor) {
            MagicColorEnum.BLUE -> blue = color
            MagicColorEnum.GREEN -> green = color
            MagicColorEnum.RED -> red = color
            MagicColorEnum.WHITE -> white = color
            else -> black = color
        }
        settingsView.updateColorButtonValue(color)
    }

    //endregion

    override fun onKeepScreenOnCheckboxClick(checked: Boolean) {
        PreferenceManager.saveScreenTimeoutDisabled(preferences, checked)
    }

    override fun onShowAppIntroClick() {
        settingsView.loadActivity(IntroActivity::class.java)
    }

    override fun onConfirmGameResetCheckboxClick(checked: Boolean) {
        PreferenceManager.saveConfirmGameReset(preferences, checked)
    }

    //region Reset Button Click

    override fun onResetButtonClick() {
        settingsView.loadResetConfirmationDialog()
    }

    override fun onResetButtonConfirm() {
        // Reset all Settings
        PreferenceManager.resetLongClickPoints(preferences)
        PreferenceManager.resetLifepoints(preferences)
        PreferenceManager.resetColors(preferences)

        settingsView.returnToPrevActivity()
    }

    override fun onResetButtonCancel() {
        // Nothing to do here
    }

    //endregion
}
