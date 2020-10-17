package com.marceljurtz.lifecounter.views.Game

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.views.Base.Presenter
import com.marceljurtz.lifecounter.enums.ClickTypeEnum
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.Game
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.OperatorEnum
import com.marceljurtz.lifecounter.models.Player
import com.marceljurtz.lifecounter.enums.PlayerIdEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity

class GamePresenter internal constructor(private val view: IGameView, preferences: SharedPreferences) : Presenter(view, preferences), IGamePresenter {

    private val game: Game

    private var settingsVisible: Boolean = false
    private var poisonVisible: Boolean = false

    private val SCREEN_SMALL = 1
    private val SCREEN_NORMAL = 2
    private val SCREEN_LARGE = 3
    private val SCREEN_XLARGE = 4

    private var hideOtherControlsWhenSettingsDisplayed = false

    init {

        val screenLayout = view.screenSize
        // Configuration.SCREENLAYOUT_SIZE_LARGE;   --> 3
        // Configuration.SCREENLAYOUT_SIZE_NORMAL;  --> 2
        // Configuration.SCREENLAYOUT_SIZE_SMALL;   --> 1
        // Configuration.SCREENLAYOUT_SIZE_XLARGE;  --> 4

        if (screenLayout != SCREEN_XLARGE && view.playerAmount == 4) hideOtherControlsWhenSettingsDisplayed = true

        game = Game(_preferences, view.playerAmount)

        // Initiate default colors
        view.initColorButton(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences))
        view.initColorButton(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences))
        view.initColorButton(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences))
        view.initColorButton(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences))
        view.initColorButton(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences))

        // Settings, Energy-Saving & Poison
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, false)
        view.settingsButtonDisable()
        settingsVisible = false

        view.poisonButtonDisable()
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed)
        poisonVisible = false
    }

    override fun onPause() {
        game.saveGameState(_preferences)
    }

    override fun onResume() {

        if (view.playerAmount == 4) {
            game.loadGameState(_preferences, 4)
        } else {
            game.loadGameState(_preferences, 2)
        }

        settingsVisible = false
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible)
        view.settingsButtonDisable()

        poisonVisible = false
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed)
        view.poisonButtonDisable()

        // Reset PowerSave Mode
        _powerSaveEnabled = true
        onMenuEntryEnergySaveTap()

        for (player in game.players!!) {
            player!!.setColor(Color(player.colorOrDefault.basecolor!!, PreferenceManager.getCustomizedColorOrDefault(player.colorOrDefault.basecolor!!, _preferences)))
            view.setLifepoints(player.playerIdEnum!!, String.format("%02d", player.lifePoints))
            view.setPoisonpoints(player.playerIdEnum!!, String.format("%02d", player.poisonPoints))
            view.setLayoutColor(player.playerIdEnum!!, player.colorOrDefault.intValue)
        }

        view.initColorButton(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences))
        view.initColorButton(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences))
        view.initColorButton(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences))
        view.initColorButton(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences))
        view.initColorButton(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences))

        if (PreferenceManager.getScreenTimeoutDisabled(_preferences)) {
            view.disableScreenTimeout()
        } else {
            view.enableScreenTimeout()
        }

        view.setConfirmGameReset(PreferenceManager.getConfirmGameReset(_preferences))

        view.hideNavigationDrawer()
    }

    override fun onLifeUpdate(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum) {
        game.updateLifepoints(playerIdEnum, clickTypeEnum, operatorEnum)
        val points = game.getPlayerLifepoints(playerIdEnum)

        val pointsStr = String.format("%02d", points)
        view.setLifepoints(playerIdEnum, pointsStr)
    }

    override fun onPoisonUpdate(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum) {
        game.updatePoisonpoints(playerIdEnum, clickTypeEnum, operatorEnum)
        val points = game.getPlayerPoisonpoints(playerIdEnum)

        val pointsStr = String.format("%02d", points)
        view.setPoisonpoints(playerIdEnum, pointsStr)
    }

    override fun onColorButtonClick(playerIdEnum: PlayerIdEnum, color: MagicColorEnum, clickTypeEnum: ClickTypeEnum) {
        if (clickTypeEnum == ClickTypeEnum.SHORT) {

            // Disable PowerSaveMode if enabled
            if (_powerSaveEnabled) onMenuEntryEnergySaveTap()

            val newColor: Color

            when (color) {
                MagicColorEnum.BLUE -> newColor = Color(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences))
                MagicColorEnum.GREEN -> newColor = Color(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences))
                MagicColorEnum.RED -> newColor = Color(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences))
                MagicColorEnum.WHITE -> newColor = Color(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences))
                else -> newColor = Color(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences))
            }

            when (playerIdEnum) {
                PlayerIdEnum.ONE -> {
                    game.player1.setColor(newColor)
                    game.player1.setColor(newColor)
                }
                PlayerIdEnum.TWO -> {
                    game.player2.setColor(newColor)
                    game.player2.setColor(newColor)
                }
                PlayerIdEnum.THREE -> {
                    game.player3.setColor(newColor)
                    game.player3.setColor(newColor)
                }
                PlayerIdEnum.FOUR -> {
                    game.player4.setColor(newColor)
                    game.player4.setColor(newColor)
                }
            }

            view.setLayoutColor(playerIdEnum, newColor.intValue)
        } else if (clickTypeEnum == ClickTypeEnum.LONG && color == MagicColorEnum.BLACK) {
            onMenuEntryEnergySaveTap()
        }
    }

    override fun onPoisonButtonClick() {
        // Activation only possible on small screens when settings are hidden
        if (!hideOtherControlsWhenSettingsDisplayed || !settingsVisible || view.playerAmount == 2) {
            poisonVisible = !poisonVisible
            if (poisonVisible) {
                view.enablePoisonControls(hideOtherControlsWhenSettingsDisplayed)
                view.poisonButtonEnable()
            } else {
                view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed)
                view.poisonButtonDisable()
            }
        }
    }

    override fun onSettingsButtonClick(clickTypeEnum: ClickTypeEnum) {
        if (clickTypeEnum == ClickTypeEnum.LONG) {
            view.loadActivity(SettingsActivity::class.java)
        } else {
            settingsVisible = !settingsVisible
            if (settingsVisible) {
                view.enableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible && hideOtherControlsWhenSettingsDisplayed)
                view.settingsButtonEnable()
            } else {
                view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible && hideOtherControlsWhenSettingsDisplayed)
                view.settingsButtonDisable()
            }
        }
    }

    override fun onResetButtonClick() {

        game.resetLifePoints()

        settingsVisible = false
        poisonVisible = false
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed)
        view.settingsButtonDisable()
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible)
        view.poisonButtonDisable()

        view.setLifepoints(PlayerIdEnum.ONE, String.format("%02d", game.player1.lifePoints))
        view.setLifepoints(PlayerIdEnum.TWO, String.format("%02d", game.player2.lifePoints))

        view.setPoisonpoints(PlayerIdEnum.ONE, String.format("%02d", game.player1.poisonPoints))
        view.setPoisonpoints(PlayerIdEnum.TWO, String.format("%02d", game.player2.poisonPoints))

        if (view.playerAmount == 4) {
            view.setLifepoints(PlayerIdEnum.THREE, String.format("%02d", game.player3.lifePoints))
            view.setLifepoints(PlayerIdEnum.FOUR, String.format("%02d", game.player3.lifePoints))

            view.setPoisonpoints(PlayerIdEnum.THREE, String.format("%02d", game.player3.poisonPoints))
            view.setPoisonpoints(PlayerIdEnum.FOUR, String.format("%02d", game.player4.poisonPoints))
        }
    }
}
