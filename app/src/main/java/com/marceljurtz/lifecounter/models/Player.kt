package com.marceljurtz.lifecounter.models

import android.content.SharedPreferences
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.Gson
import com.marceljurtz.lifecounter.enums.MagicColorEnum
import com.marceljurtz.lifecounter.enums.PlayerIdEnum

import java.util.ArrayList

class Player : Parcelable {
    var lifePoints: Int = 0
        private set
    var poisonPoints: Int = 0
        private set
    var playerIdEnum: PlayerIdEnum? = null
        private set
    private var playerIdentification: String? = null
    var allCounters: ArrayList<Counter>? = null
        private set
    private var color: Color = Color.getDefaultColor(MagicColorEnum.BLACK);

    private val DEFAULT_LIFEPOINTS = 20

    val json: String
        get() = Gson().toJson(this)

    val colorOrDefault: Color
        get() {
            return color
        }

    constructor(id: PlayerIdEnum) {
        this.playerIdEnum = id
        this.lifePoints = DEFAULT_LIFEPOINTS
        this.poisonPoints = 0
        allCounters = ArrayList()
        color = Color.getDefaultColor(MagicColorEnum.BLACK)
    }

    private constructor(source: Parcel) {
        lifePoints = source.readInt()
        poisonPoints = source.readInt()
        playerIdEnum = PlayerIdEnum.valueOf(source.readString())
        playerIdentification = source.readString()
        color = Color.getDefaultColor(MagicColorEnum.valueOf(source.readString()))
        allCounters = ArrayList()
        source.readList(allCounters, Counter::class.java.classLoader)
    }

    fun resetPoints(preferences: SharedPreferences) {
        this.lifePoints = PreferenceManager.getDefaultLifepoints(preferences)
        this.poisonPoints = 0
    }

    fun updateLifepoints(amount: Int) {
        this.lifePoints += amount
        if (this.lifePoints < PreferenceManager.minLife) {
            this.lifePoints = PreferenceManager.minLife
        } else if (this.lifePoints > PreferenceManager.maxLife) {
            this.lifePoints = PreferenceManager.maxLife
        }
    }

    fun updatePoisonpoints(amount: Int) {
        this.poisonPoints += amount
        if (this.poisonPoints < PreferenceManager.minPoison) {
            this.poisonPoints = PreferenceManager.minPoison
        } else if (this.poisonPoints > PreferenceManager.maxPoison) {
            this.lifePoints = PreferenceManager.maxPoison
        }
    }

    fun getPlayerIdentification(): String {
        if (playerIdentification == null || playerIdentification!!.trim { it <= ' ' } === "") {
            when (playerIdEnum) {
                PlayerIdEnum.ONE -> playerIdentification = "Player 1"
                PlayerIdEnum.TWO -> playerIdentification = "Player 2"
                PlayerIdEnum.THREE -> playerIdentification = "Player 3"
                PlayerIdEnum.FOUR -> playerIdentification = "Player 4"
                else -> playerIdentification = ""
            }

            return playerIdentification!!;
        }
        return "";
    }

    fun setPlayerIdentification(identification: String) {
        playerIdentification = identification
    }

    fun addCounter(c: Counter) {
        c.identifier = playerIdEnum!!.toString() + "_" + allCounters!!.size
        allCounters!!.add(c)
    }

    fun removeCounter(c: Counter?) {
        var c = c
        allCounters!!.remove(c)
        c = null
    }

    fun updateCounter(counter: Counter) {
        for (c in allCounters!!) {
            if (c.identifier === counter.identifier) {

                c.atk = counter.atk
                c.def = counter.def
                c.description = counter.description

                return
            }
        }
    }

    fun clearCounters() {
        allCounters!!.clear()
    }

    fun setColor(color: Color) {
        this.color = color
    }

    override fun toString(): String {
        return getPlayerIdentification()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(lifePoints)
        dest.writeInt(poisonPoints)
        dest.writeString(playerIdEnum!!.name)
        dest.writeString(playerIdentification)
        dest.writeValue(color)
        dest.writeList(allCounters)
    }

    companion object {

        fun GetInstanceByJson(json: String): Player {
            return Gson().fromJson(json, Player::class.java)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<Player> = object : Parcelable.Creator<Player> {
            override fun createFromParcel(`in`: Parcel): Player {
                return Player(`in`)
            }

            override fun newArray(size: Int): Array<Player?> {
                return arrayOfNulls(size)
            }
        }
    }
}
