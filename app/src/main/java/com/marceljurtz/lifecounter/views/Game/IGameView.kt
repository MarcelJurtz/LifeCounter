package com.marceljurtz.lifecounter.views.Game

import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

interface IGameView : IView {

    val playerAmount: Int

    val screenSize: Int
    // Set color of button, identified by original magic color
    fun initColorButton(colorLocation: MagicColorEnum, color: Int)

    // Set players background color TODO Validate possible duplicate
    fun setLayoutColor(playerIdEnum: PlayerIdEnum, color: Int)

    // Disable Screen timeout
    fun disableScreenTimeout()

    fun enableScreenTimeout()

    // Set life- / poisonpoints
    fun setLifepoints(id: PlayerIdEnum, points: String)

    fun setPoisonpoints(id: PlayerIdEnum, points: String)

    // Enable / Disable energy saving mode
    fun enableEnergySaving(powerSaveColor: Int, powerSaveTextColor: Int)

    fun disableEnergySaving(defaultBlack: Int, regularTextColor: Int)

    // Launch settings activity
    override fun loadActivity(c: Class<*>)

    // Enable / Disable color controls
    fun settingsButtonEnable()

    fun enableSettingsControls(hideLifecountControls: Boolean, hidePoisonControls: Boolean)
    fun settingsButtonDisable()
    fun disableSettingsControls(showOtherControls: Boolean, showPoisonControls: Boolean)

    // Enable / Disable poison controls
    fun enablePoisonControls(rearrangeLifepoints: Boolean)

    fun poisonButtonEnable()
    fun disablePoisonControls(rearrangeLifepoints: Boolean)
    fun poisonButtonDisable()

    // Drawer Layout Interaction
    fun setDrawerTextPowerSaving(shouldBeEnabled: Boolean)

    fun hideNavigationDrawer()

    fun setConfirmGameReset(confirmGameReset: Boolean)

    fun restartActivity()
}
