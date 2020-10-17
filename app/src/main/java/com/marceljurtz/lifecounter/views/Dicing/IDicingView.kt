package com.marceljurtz.lifecounter.views.Dicing

import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.models.Color

interface IDicingView : IView {
    fun setDicingText(text: String)
    override fun loadActivity(c: Class<*>)
    fun setBackgroundColor(color: Color)
}
