package com.marceljurtz.lifecounter.views.Settings

import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.enums.MagicColorEnum

interface ISettingsView : IView {
    // Lifepoints and Longclickpoints Getter & Setter
    var lifepoints: String
    var longClickPoints: String

    fun returnToPrevActivity()
    override fun loadActivity(c: Class<*>)

    fun loadResetConfirmationDialog()
    fun loadColorPickerDialog(color: MagicColorEnum, r: Int, g: Int, b: Int)
    fun updateColorButtonValue(color: Color)

    fun setKeepScreenOnCheckbox(checked: Boolean)
    fun setConfirmGameResetCheckbox(checked: Boolean)
}
