package com.marceljurtz.lifecounter.views.Settings

import com.marceljurtz.lifecounter.contracts.base.IPresenter
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.enums.MagicColorEnum

interface ISettingsPresenter : IPresenter {
    fun onBackButtonClick()
    fun onCancelButtonClick()
    fun onResetButtonClick()
    fun onColorSelectButtonClick(color: MagicColorEnum)
    fun onColorSelectValueUpdate(color: Color)
    fun onKeepScreenOnCheckboxClick(checked: Boolean)
    fun onConfirmGameResetCheckboxClick(checked: Boolean)
    fun onShowAppIntroClick()

    fun onResetButtonConfirm()
    fun onResetButtonCancel()

    override fun onMenuEntryTwoPlayerTap()
    override fun onMenuEntryFourPlayerTap()
    override fun onMenuEntryCounterManagerTap()
    override fun onMenuEntryDicingTap()
    override fun onMenuEntryAboutTap()
}
