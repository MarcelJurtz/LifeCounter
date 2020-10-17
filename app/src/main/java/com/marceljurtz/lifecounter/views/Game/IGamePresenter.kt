package com.marceljurtz.lifecounter.views.Game

import com.marceljurtz.lifecounter.contracts.base.IPresenter
import com.marceljurtz.lifecounter.enums.ClickTypeEnum
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.OperatorEnum
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

interface IGamePresenter : IPresenter {

    // Updating players life- and poisonpoints
    fun onLifeUpdate(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum)

    fun onPoisonUpdate(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum)

    // Change player background color
    fun onColorButtonClick(playerIdEnum: PlayerIdEnum, color: MagicColorEnum, clickTypeEnum: ClickTypeEnum)

    // Enable / Disable poison controls
    fun onPoisonButtonClick()

    // Enable / Disable color controls
    fun onSettingsButtonClick(clickTypeEnum: ClickTypeEnum)

    // Hit reset button
    fun onResetButtonClick()
}