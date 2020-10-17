package com.marceljurtz.lifecounter.models

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.enums.ClickTypeEnum
import com.marceljurtz.lifecounter.enums.OperatorEnum
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

class Game(private val preferences: SharedPreferences, playerAmount: Int) {

    var players: Array<Player?>? = null
        private set

    val player1: Player
        get() = players!!.get(0)!!
    val player2: Player
        get() = players!!.get(1)!!
    val player3: Player
        get() = players!!.get(2)!!
    val player4: Player
        get() = players!!.get(3)!!

    init {
        this.players = arrayOfNulls(playerAmount)
    }

    fun saveGameState(preferences: SharedPreferences) {
        if (players!!.size == 4) {
            PreferenceManager.save4PlayerPointsData(preferences, players!!)
        } else {
            PreferenceManager.save2PlayerPointsData(preferences, players!!)
        }
    }

    fun loadGameState(preferences: SharedPreferences, playeramount: Int) {
        if (playeramount == 4) {
            players = PreferenceManager.load4PlayerPointsData(preferences)
        } else {
            players = PreferenceManager.load2PlayerPointsData(preferences)
        }
    }

    fun updateLifepoints(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum) {

        var amount = PreferenceManager.defaultShortclickPoints
        if (clickTypeEnum == ClickTypeEnum.LONG) amount = PreferenceManager.getLongclickPoints(preferences)

        if (operatorEnum == OperatorEnum.SUBSTRACT) amount *= -1

        when (playerIdEnum) {
            PlayerIdEnum.ONE -> players!![0]!!.updateLifepoints(amount)
            PlayerIdEnum.TWO -> players!![1]!!.updateLifepoints(amount)
            PlayerIdEnum.THREE -> players!![2]!!.updateLifepoints(amount)
            PlayerIdEnum.FOUR -> players!![3]!!.updateLifepoints(amount)
            else -> {
            }
        }// Nothing to do
    }

    fun resetLifePoints() {
        for (player in players!!) {
            player!!.resetPoints(preferences)
            player!!.updatePoisonpoints(0)
        }
    }

    fun updatePoisonpoints(playerIdEnum: PlayerIdEnum, clickTypeEnum: ClickTypeEnum, operatorEnum: OperatorEnum) {

        var amount = PreferenceManager.defaultShortclickPoints
        if (clickTypeEnum == ClickTypeEnum.LONG) amount = PreferenceManager.getLongclickPoints(preferences)

        if (operatorEnum == OperatorEnum.SUBSTRACT) amount *= -1

        when (playerIdEnum) {
            PlayerIdEnum.ONE -> players!![0]!!.updatePoisonpoints(amount)
            PlayerIdEnum.TWO -> players!![1]!!.updatePoisonpoints(amount)
            PlayerIdEnum.THREE -> players!![2]!!.updatePoisonpoints(amount)
            PlayerIdEnum.FOUR -> players!![3]!!.updatePoisonpoints(amount)
            else -> {
            }
        }// Nothing to do
    }

    fun getPlayerLifepoints(playerIdEnum: PlayerIdEnum): Int {
        when (playerIdEnum) {
            PlayerIdEnum.ONE -> return players!![0]!!.lifePoints
            PlayerIdEnum.TWO -> return players!![1]!!.lifePoints
            PlayerIdEnum.THREE -> return players!![2]!!.lifePoints
            PlayerIdEnum.FOUR -> return players!![3]!!.lifePoints
            else -> return 0
        }
    }

    fun getPlayerPoisonpoints(playerIdEnum: PlayerIdEnum): Int {
        when (playerIdEnum) {
            PlayerIdEnum.ONE -> return players!![0]!!.poisonPoints
            PlayerIdEnum.TWO -> return players!![1]!!.poisonPoints
            PlayerIdEnum.THREE -> return players!![2]!!.poisonPoints
            PlayerIdEnum.FOUR -> return players!![3]!!.poisonPoints
            else -> return 0
        }
    }
}
