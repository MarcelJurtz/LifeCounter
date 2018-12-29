/*
Custom Color Manager for Magic Lifecounter

* MagicColor for identification of original color
* Int-Value for setting backgroundcolors and stuff
* Int-Value can be customized and is saved via SharedPreferences
* For this, use the prefString-member
 */

package com.marceljurtz.lifecounter.models;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.enums.MagicColorEnum;

public class Color {

    //region Default colors

    public static final int DEFAULT_BLACK = android.graphics.Color.parseColor("#CCC2C0");
    public static final int DEFAULT_BLUE = android.graphics.Color.parseColor("#AAE0FA");
    public static final int DEFAULT_GREEN = android.graphics.Color.parseColor("#9BD3AE");
    public static final int DEFAULT_RED = android.graphics.Color.parseColor("#FAAA8F");
    public static final int DEFAULT_WHITE = android.graphics.Color.parseColor("#FFFCD6");

    public static final int COLOR_ENERGY_SAVE = android.graphics.Color.parseColor("#000000");

    //endregion

    //region Constructors and properties

    private int intValue;
    private MagicColorEnum baseColor;

    public Color(MagicColorEnum baseColor, SharedPreferences preferences) {
        this.baseColor = baseColor;
        this.intValue = PreferenceManager.getCustomizedColorOrDefault(baseColor, preferences);
    }

    public Color(MagicColorEnum color, int intValue) {
        this.baseColor = color;
        this.intValue = intValue;
    }

    //endregion

    //region Getter
    public String getPreferenceString() {
        switch(baseColor) {
            case BLUE:
                return PreferenceManager.PREF_COLOR_BLUE;
            case GREEN:
                return PreferenceManager.PREF_COLOR_GREEN;
            case RED:
                return PreferenceManager.PREF_COLOR_RED;
            case WHITE:
                return PreferenceManager.PREF_COLOR_WHITE;
            default:
                return PreferenceManager.PREF_COLOR_BLACK;
        }
    }

    public MagicColorEnum getBasecolor() {
        return baseColor;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getHexString() {
        return String.format("#%06X", 0xFFFFFF & intValue);
    }

    //endregion

    //region Static members

    public static Color getDefaultColor(MagicColorEnum color) {
        switch(color) {
            case BLUE:
                return new Color(MagicColorEnum.BLUE, DEFAULT_BLUE);
            case GREEN:
                return new Color(MagicColorEnum.GREEN, DEFAULT_GREEN);
            case RED:
                return new Color(MagicColorEnum.RED, DEFAULT_RED);
            case WHITE:
                return new Color(MagicColorEnum.WHITE, DEFAULT_WHITE);
            default:
                return new Color(MagicColorEnum.BLACK, DEFAULT_BLACK);
        }
    }

    public static int getDefaultColorInt(MagicColorEnum color) {
        return getDefaultColor(color).intValue;
    }

    // Get RGB version for int color
    public static int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }

    //endregion
}
