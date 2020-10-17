/*
Custom Color Manager for Magic Lifecounter

* MagicColor for identification of original color
* Int-Value for setting backgroundcolors and stuff
* Int-Value can be customized and is saved via SharedPreferences
* For this, use the prefString-member
 */

package com.marceljurtz.lifecounter.models

import android.content.SharedPreferences

import com.marceljurtz.lifecounter.enums.MagicColorEnum

class Color {

    //endregion

    //region Constructors and properties

    var intValue: Int = 0
        private set
    var basecolor: MagicColorEnum? = null
        private set

    //endregion

    //region Getter
    val preferenceString: String
        get() {
            when (basecolor) {
                MagicColorEnum.BLUE -> return PreferenceManager.PREF_COLOR_BLUE
                MagicColorEnum.GREEN -> return PreferenceManager.PREF_COLOR_GREEN
                MagicColorEnum.RED -> return PreferenceManager.PREF_COLOR_RED
                MagicColorEnum.WHITE -> return PreferenceManager.PREF_COLOR_WHITE
                else -> return PreferenceManager.PREF_COLOR_BLACK
            }
        }

    val hexString: String
        get() = String.format("#%06X", 0xFFFFFF and intValue)

    constructor(baseColor: MagicColorEnum, preferences: SharedPreferences) {
        this.basecolor = baseColor
        this.intValue = PreferenceManager.getCustomizedColorOrDefault(baseColor, preferences)
    }

    constructor(color: MagicColorEnum, intValue: Int) {
        this.basecolor = color
        this.intValue = intValue
    }

    companion object {

        //region Default colors

        val DEFAULT_BLACK = android.graphics.Color.parseColor("#CCC2C0")
        val DEFAULT_BLUE = android.graphics.Color.parseColor("#AAE0FA")
        val DEFAULT_GREEN = android.graphics.Color.parseColor("#9BD3AE")
        val DEFAULT_RED = android.graphics.Color.parseColor("#FAAA8F")
        val DEFAULT_WHITE = android.graphics.Color.parseColor("#FFFCD6")

        val COLOR_ENERGY_SAVE = android.graphics.Color.parseColor("#000000")

        //endregion

        //region Static members

        fun getDefaultColor(color: MagicColorEnum): Color {
            when (color) {
                MagicColorEnum.BLUE -> return Color(MagicColorEnum.BLUE, DEFAULT_BLUE)
                MagicColorEnum.GREEN -> return Color(MagicColorEnum.GREEN, DEFAULT_GREEN)
                MagicColorEnum.RED -> return Color(MagicColorEnum.RED, DEFAULT_RED)
                MagicColorEnum.WHITE -> return Color(MagicColorEnum.WHITE, DEFAULT_WHITE)
                else -> return Color(MagicColorEnum.BLACK, DEFAULT_BLACK)
            }
        }

        fun getDefaultColorInt(color: MagicColorEnum): Int {
            return getDefaultColor(color).intValue
        }

        // Get RGB version for int color
        fun getRGB(color: Int): IntArray {
            val rgb = IntArray(3)
            rgb[0] = color shr 16 and 0xFF
            rgb[1] = color shr 8 and 0xFF
            rgb[2] = color shr 0 and 0xFF
            return rgb
        }
    }

    //endregion
}
