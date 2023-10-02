/*

    Preference Manager for saving, loading and restoring default values

    *
 */

package com.marceljurtz.lifecounter.models

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

import java.util.ArrayList

object PreferenceManager {

    //region Default values

    private val DEFAULT_LIFEPOINTS = 20
    private val DEFAULT_LONGCLICK_POINTS = 5
    // Get Shortclickpoints
    val defaultShortclickPoints = 1

    private val DEFAULT_PLAYER_AMOUNT = 2

    private val DEFAULT_KEEP_SCREEN_ON = true
    private val DEFAULT_CONFIRM_GAME_RESET = false

    private val SUFFIX_2PLAYER = "_POINTS2"
    private val SUFFIX_4PLAYER = "_POINTS4"

    // MAX and MIN Values
    //endregion

    //region Min / Max values

    val maxPoison = 25
    val minPoison = 0
    val maxLife = 1000
    val minLife = -100

    //endregion

    //region Preference strings

    val PREFS = "MTG_SETTINGS"
    val PREF_COLOR_BLACK = "COLOR_BLACK"
    val PREF_COLOR_BLUE = "COLOR_BLUE"
    val PREF_COLOR_GREEN = "COLOR_GREEN"
    val PREF_COLOR_RED = "COLOR_RED"
    val PREF_COLOR_WHITE = "COLOR_WHITE"

    private val PREF_LONG_CLICK_POINTS = "DEFAULT_LONG_CLICK_POINTS"
    private val PREF_LIFEPOINTS = "DEFAULT_LIFEPOINTS"

    private val PREF_PLAYER_AMOUNT = "PLAYER_AMOUNT"

    private val PREF_KEEP_SCREEN_ON = "KEEP_SCREEN_ON"
    private val PREF_CONFIRM_GAME_RESET = "CONFIRM_GAME_RESET"

    private val PREF_VERSIONNUMBER = "VERSION"

    // Powersave values
    val powerSave = android.graphics.Color.parseColor("#000000")
    val powerSaveTextcolor = android.graphics.Color.parseColor("#CCC2C0")
    val regularTextcolor = android.graphics.Color.parseColor("#161618")

    //endregion

    //region Custom colors

    // Set custom color
    fun saveColor(preferences: SharedPreferences, color: Color) {
        val editor = preferences.edit()
        editor.putInt(color.preferenceString, color.intValue)
        editor.apply()
    }

    // Reset all custom colors
    fun resetColors(preferences: SharedPreferences) {
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.BLACK))
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.BLUE))
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.GREEN))
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.RED))
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.WHITE))
    }

    // Getter for default colors
    fun getCustomizedColorOrDefault(color: MagicColorEnum, preferences: SharedPreferences): Int {
        when (color) {
            MagicColorEnum.BLUE -> return preferences.getInt(PREF_COLOR_BLUE, Color.DEFAULT_BLUE)
            MagicColorEnum.GREEN -> return preferences.getInt(PREF_COLOR_GREEN, Color.DEFAULT_GREEN)
            MagicColorEnum.RED -> return preferences.getInt(PREF_COLOR_RED, Color.DEFAULT_RED)
            MagicColorEnum.WHITE -> return preferences.getInt(PREF_COLOR_WHITE, Color.DEFAULT_WHITE)
            else -> return preferences.getInt(PREF_COLOR_BLACK, Color.DEFAULT_BLACK)
        }
    }

    //endregion

    //region Amount of Players
    fun getPlayerAmount(preferences: SharedPreferences): Int {
        return preferences.getInt(PREF_PLAYER_AMOUNT, DEFAULT_PLAYER_AMOUNT)
    }

    fun saveDefaultPlayerAmount(preferences: SharedPreferences, playeramount: Int) {
        if (playeramount == 2 || playeramount == 4) {
            val editor = preferences.edit()
            editor.putInt(PREF_PLAYER_AMOUNT, playeramount)
            editor.apply()
        }
    }
    //endregion

    //region Keep Screen On

    fun getScreenTimeoutDisabled(preferences: SharedPreferences): Boolean {
        return preferences.getBoolean(PREF_KEEP_SCREEN_ON, DEFAULT_KEEP_SCREEN_ON)
    }

    fun saveScreenTimeoutDisabled(preferences: SharedPreferences, timeoutDisabled: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(PREF_KEEP_SCREEN_ON, timeoutDisabled)
        editor.apply()
    }

    //endregion

    //region Confirm Game Reset

    fun getConfirmGameReset(preferences: SharedPreferences): Boolean {
        return preferences.getBoolean(PREF_CONFIRM_GAME_RESET, DEFAULT_CONFIRM_GAME_RESET)
    }

    fun saveConfirmGameReset(preferences: SharedPreferences, confirmGameReset: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(PREF_CONFIRM_GAME_RESET, confirmGameReset)
        editor.apply()
    }

    //endregion

    //region Lifepoints and Longclickpoints

    // Get Lifepoints
    fun getDefaultLifepoints(preferences: SharedPreferences): Int {
        return preferences.getInt(PREF_LIFEPOINTS, DEFAULT_LIFEPOINTS)
    }

    // Set Lifepoints
    fun saveDefaultLifepoints(preferences: SharedPreferences, lifepoints: Int) {
        val editor = preferences.edit()
        editor.putInt(PREF_LIFEPOINTS, lifepoints)
        editor.apply()
    }

    // Reset Lifepoints
    fun resetLifepoints(preferences: SharedPreferences) {
        saveDefaultLifepoints(preferences, DEFAULT_LIFEPOINTS)
    }

    // Get Longclickpoints
    fun getLongclickPoints(preferences: SharedPreferences): Int {
        return preferences.getInt(PREF_LONG_CLICK_POINTS, DEFAULT_LONGCLICK_POINTS)
    }

    // Set Longclickpoints
    fun saveDefaultLongClickPoints(preferences: SharedPreferences, longClickPoints: Int) {
        val editor = preferences.edit()
        editor.putInt(PREF_LONG_CLICK_POINTS, longClickPoints)
        editor.apply()
    }

    // Reset Longclickpoints
    fun resetLongClickPoints(preferences: SharedPreferences) {
        saveDefaultLongClickPoints(preferences, DEFAULT_LONGCLICK_POINTS)
    }

    //endregion

    //region Player Data (points + counters)
    fun savePlayerCounterData(preferences: SharedPreferences, players: List<Player>) {
        val editor = preferences.edit()

        for (player in players) {
            editor.putString(player.playerIdEnum.toString(), player.json)
        }

        editor.apply()
    }

    fun loadPlayerCounterData(preferences: SharedPreferences): ArrayList<Player> {
        val players = ArrayList<Player>()

        val p1 = preferences.getString(PlayerIdEnum.ONE.toString(), "0")
            ?.let { Player.GetInstanceByJson(it) }
        val p2 = preferences.getString(PlayerIdEnum.TWO.toString(), "0")
            ?.let { Player.GetInstanceByJson(it) }
        val p3 = preferences.getString(PlayerIdEnum.THREE.toString(), "0")
            ?.let { Player.GetInstanceByJson(it) }
        val p4 = preferences.getString(PlayerIdEnum.FOUR.toString(), "0")
            ?.let { Player.GetInstanceByJson(it) }

        if (p1 != null)
            players.add(p1)
        else
            players.add(Player(PlayerIdEnum.ONE))
        if (p2 != null)
            players.add(p2)
        else
            players.add(Player(PlayerIdEnum.TWO))
        if (p3 != null)
            players.add(p3)
        else
            players.add(Player(PlayerIdEnum.THREE))
        if (p4 != null)
            players.add(p4)
        else
            players.add(Player(PlayerIdEnum.FOUR))

        return players
    }

    fun save2PlayerPointsData(preferences: SharedPreferences, players: Array<Player?>) {
        val editor = preferences.edit()

        for (player in players) {
            editor.putString(player!!.playerIdEnum.toString() + SUFFIX_2PLAYER, player.json)
        }

        editor.apply()
    }

    fun load2PlayerPointsData(preferences: SharedPreferences): Array<Player?> {
        val players = arrayOfNulls<Player>(2)

        val p1 = preferences.getString(PlayerIdEnum.ONE.toString() + SUFFIX_2PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }
        val p2 = preferences.getString(PlayerIdEnum.TWO.toString() + SUFFIX_2PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }

        if (p1 != null)
            players[0] = p1
        else
            players[0] = Player(PlayerIdEnum.ONE)

        if (p2 != null)
            players[1] = p2
        else
            players[1] = Player(PlayerIdEnum.TWO)

        return players
    }

    fun save4PlayerPointsData(preferences: SharedPreferences, players: Array<Player?>) {
        val editor = preferences.edit()

        for (player in players) {
            editor.putString(player!!.playerIdEnum.toString() + SUFFIX_4PLAYER, player.json)
        }

        editor.apply()
    }

    fun load4PlayerPointsData(preferences: SharedPreferences): Array<Player?> {
        val players = arrayOfNulls<Player>(4)

        val p1 = preferences.getString(PlayerIdEnum.ONE.toString() + SUFFIX_4PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }
        val p2 = preferences.getString(PlayerIdEnum.TWO.toString() + SUFFIX_4PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }
        val p3 = preferences.getString(PlayerIdEnum.THREE.toString() + SUFFIX_4PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }
        val p4 = preferences.getString(PlayerIdEnum.FOUR.toString() + SUFFIX_4PLAYER, null)
            ?.let { Player.GetInstanceByJson(it) }

        if (p1 != null)
            players[0] = p1
        else
            players[0] = Player(PlayerIdEnum.ONE)

        if (p2 != null)
            players[1] = p2
        else
            players[1] = Player(PlayerIdEnum.TWO)

        if (p3 != null)
            players[2] = p3
        else
            players[2] = Player(PlayerIdEnum.THREE)

        if (p4 != null)
            players[3] = p4
        else
            players[3] = Player(PlayerIdEnum.FOUR)

        return players
    }
}
