package com.marceljurtz.lifecounter.views.Counter

import android.widget.LinearLayout

import com.marceljurtz.lifecounter.contracts.base.IView
import com.marceljurtz.lifecounter.enums.CounterType
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.Counter
import com.marceljurtz.lifecounter.models.Player
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

import java.util.ArrayList

interface ICounterView : IView {
    fun setBackgroundColor(color: Color)
    fun setPlayerIdentificationText(playerIdEnum: PlayerIdEnum, headerText: String)
    fun addCounter(playerIdEnum: PlayerIdEnum, counter: Counter)

    fun deleteCounter(counterLayout: LinearLayout, deleteParent: Boolean)
    fun deleteAllCounters()
    fun deleteAllCountersForPlayer(playerIdEnum: PlayerIdEnum)

    fun updateCounterView(player: Player, counter: Counter)

    fun loadPlayerIdentificationDialog(playerIdEnum: PlayerIdEnum, playername: String)
    fun loadPlayerDeletionDialog(playerIdEnum: PlayerIdEnum)
    fun loadCounterAddDialog(players: ArrayList<Player>, counterType: CounterType)
    fun loadCounterEditDialog(counterLayout: LinearLayout, player: Player, counter: Counter)
    fun loadCounterDeletionDialog(linearLayout: LinearLayout)
}