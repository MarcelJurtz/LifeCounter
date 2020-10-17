package com.marceljurtz.lifecounter.views.Counter

import android.widget.LinearLayout

import com.marceljurtz.lifecounter.models.Counter
import com.marceljurtz.lifecounter.contracts.base.IPresenter
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

interface ICounterPresenter : IPresenter {
    fun onFabCtTap()
    fun onFabPwTap()
    fun addCounter(playerIdEnum: PlayerIdEnum, counter: Counter)

    fun onCounterTap(counterWrapper: LinearLayout)
    fun onCounterLongTap(counterWrapper: LinearLayout)
    fun onCounterEditCompleted(playerIdEnum: PlayerIdEnum, oldCounterIdentifier: String, newCounter: Counter)

    fun onPlayerIdentificationTap(playerIdEnum: PlayerIdEnum)
    fun onPlayerIdentificationLongTap(playerIdEnum: PlayerIdEnum)
    fun onPlayerIdentificationChangeConfirmed(playerIdEnum: PlayerIdEnum, newIdentification: String)
    fun onPlayerDeletionConfirmed(playerIdEnum: PlayerIdEnum)  // TODO presenter logic
    fun onCounterDeletionConfirmed(counterLayout: LinearLayout)  // TODO presenter logic

    fun getPlayerIdentification(playerIdEnum: PlayerIdEnum): String
}
