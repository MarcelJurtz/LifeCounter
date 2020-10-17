package com.marceljurtz.lifecounter.views.Counter

import android.content.SharedPreferences
import android.widget.LinearLayout

import com.marceljurtz.lifecounter.enums.CounterType
import com.marceljurtz.lifecounter.views.About.AboutActivity
import com.marceljurtz.lifecounter.views.Base.Presenter
import com.marceljurtz.lifecounter.views.Dicing.DicingActivity
import com.marceljurtz.lifecounter.views.Game.GameActivity
import com.marceljurtz.lifecounter.models.Color
import com.marceljurtz.lifecounter.models.Counter
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.models.Player
import com.marceljurtz.lifecounter.enums.PlayerIdEnum
import com.marceljurtz.lifecounter.models.PreferenceManager
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity

import java.util.ArrayList
import java.util.Random

class CounterPresenter(view: ICounterView, preferences: SharedPreferences, private var players: ArrayList<Player>?) : Presenter(view, preferences), ICounterPresenter {
    private val _random: Random

    init {
        _view = view
        _random = Random()
    }

    override fun onCreate() {
        val num = _random.nextInt(4)
        var color = Color(MagicColorEnum.WHITE, _preferences)
        when (num) {
            0 -> color = Color(MagicColorEnum.BLUE, _preferences)
            1 -> color = Color(MagicColorEnum.GREEN, _preferences)
            2 -> color = Color(MagicColorEnum.RED, _preferences)
            3 -> {
            }
        }
        (_view as ICounterView).setBackgroundColor(color)
    }

    override fun onPause() {
        PreferenceManager.savePlayerCounterData(_preferences, players!!)
    }

    override fun onResume() {
        // Load items from preferences
        players = PreferenceManager.loadPlayerCounterData(_preferences)

        // Reload View
        (_view as ICounterView).deleteAllCounters()

        for (player in players!!) {

            (_view as ICounterView).setPlayerIdentificationText(player.playerIdEnum!!, player.getPlayerIdentification())

            for (counter in player.allCounters!!) {
                (_view as ICounterView).addCounter(player.playerIdEnum!!, counter)
            }
        }
    }

    override fun onFabCtTap() {
        (_view as ICounterView).loadCounterAddDialog(players!!, CounterType.Counter)
    }

    override fun onFabPwTap() {
        (_view as ICounterView).loadCounterAddDialog(players!!, CounterType.Planeswalker)
    }

    override fun addCounter(playerIdEnum: PlayerIdEnum, counter: Counter) {
        for (player in players!!) {
            if (player.playerIdEnum == playerIdEnum) {
                counter.identifier = player.playerIdEnum!!.toString() + "-" + player.allCounters!!.size
                player.addCounter(counter)
                break
            }
        }

        (_view as ICounterView).addCounter(playerIdEnum, counter)
    }

    override fun onPlayerIdentificationChangeConfirmed(playerIdEnum: PlayerIdEnum, newIdentification: String) {

        for (player in players!!) {
            if (player.playerIdEnum == playerIdEnum) {
                player.setPlayerIdentification(newIdentification)
                break
            }
        }

        (_view as ICounterView).setPlayerIdentificationText(playerIdEnum, newIdentification)
    }

    override fun getPlayerIdentification(playerIdEnum: PlayerIdEnum): String {
        for (player in players!!) {
            if (player.playerIdEnum == playerIdEnum) {
                return player.getPlayerIdentification()
            }
        }
        return ""
    }

    override fun onPlayerIdentificationTap(playerIdEnum: PlayerIdEnum) {
        (_view as ICounterView).loadPlayerIdentificationDialog(playerIdEnum, getPlayerIdentification(playerIdEnum))
    }

    override fun onPlayerIdentificationLongTap(playerIdEnum: PlayerIdEnum) {
        (_view as ICounterView).loadPlayerDeletionDialog(playerIdEnum)
    }

    override fun onPlayerDeletionConfirmed(playerIdEnum: PlayerIdEnum) {
        for (player in players!!) {
            if (player.playerIdEnum == playerIdEnum) {
                player.clearCounters()
                player.setPlayerIdentification("")
                break
            }
        }
        (_view as ICounterView).deleteAllCountersForPlayer(playerIdEnum)
    }

    override fun onCounterDeletionConfirmed(counterLayout: LinearLayout) {
        var deleteParent = false

        val currentCounter = getCounterByIdentifier(counterLayout.tag.toString())
        val currentPlayer = getPlayerByCounterIdentifier(counterLayout.tag.toString())

        if (currentPlayer != null && currentCounter != null) {
            currentPlayer.removeCounter(currentCounter)
            if (currentPlayer.allCounters!!.size <= 0) {
                deleteParent = true
            }
        }

        (_view as ICounterView).deleteCounter(counterLayout, deleteParent)
    }

    override fun onCounterTap(counterLayout: LinearLayout) {
        val identifier = counterLayout.tag.toString()
        (_view as ICounterView).loadCounterEditDialog(counterLayout, getPlayerByCounterIdentifier(identifier)!!, getCounterByIdentifier(identifier)!!)
    }

    override fun onCounterLongTap(counterLayout: LinearLayout) {
        (_view as ICounterView).loadCounterDeletionDialog(counterLayout)
    }

    override fun onCounterEditCompleted(playerIdEnum: PlayerIdEnum, oldCounterIdentifier: String, newCounter: Counter) {
        newCounter.identifier = oldCounterIdentifier
        when (playerIdEnum) {
            PlayerIdEnum.ONE -> {
                players!![0].updateCounter(newCounter)
                (_view as ICounterView).updateCounterView(players!![0], newCounter)
            }
            PlayerIdEnum.TWO -> {
                players!![1].updateCounter(newCounter)
                (_view as ICounterView).updateCounterView(players!![1], newCounter)
            }
            PlayerIdEnum.THREE -> {
                players!![2].updateCounter(newCounter)
                (_view as ICounterView).updateCounterView(players!![2], newCounter)
            }
            PlayerIdEnum.FOUR -> {
                players!![3].updateCounter(newCounter)
                (_view as ICounterView).updateCounterView(players!![3], newCounter)
            }
            else -> {
            }
        }
    }

    private fun getCounterByIdentifier(identifier: String): Counter? {
        val currentPlayer: Player?

        when (PlayerIdEnum.valueOf(identifier.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])) {
            PlayerIdEnum.ONE -> currentPlayer = players!![0]
            PlayerIdEnum.TWO -> currentPlayer = players!![1]
            PlayerIdEnum.THREE -> currentPlayer = players!![2]
            PlayerIdEnum.FOUR -> currentPlayer = players!![3]
            else -> currentPlayer = null
        }

        if (currentPlayer != null) {
            for (c in currentPlayer.allCounters!!) {
                if (c.identifier === identifier) {
                    return c
                }
            }
        }

        return null
    }

    private fun getPlayerByCounterIdentifier(identifier: String): Player? {
        when (PlayerIdEnum.valueOf(identifier.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])) {
            PlayerIdEnum.ONE -> return players!![0]
            PlayerIdEnum.TWO -> return players!![1]
            PlayerIdEnum.THREE -> return players!![2]
            PlayerIdEnum.FOUR -> return players!![3]
            else -> return null
        }
    }
}
