/*
Custom Color Manager for Magic Lifecounter

* MagicColor for identification of original color
* Int-Value for setting backgroundcolors and stuff
* Int-Value can be customized and is saved via SharedPreferences
* For this, use the prefString-member
 */

package com.marceljurtz.lifecounter.Helper;

import android.content.SharedPreferences;
import android.preference.Preference;

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
    private MagicColor baseColor;

    public Color(MagicColor baseColor, SharedPreferences preferences) {
        this.baseColor = baseColor;
        this.intValue = PreferenceManager.GetCustomizedColorOrDefault(baseColor, preferences);
    }

    public Color(MagicColor color, int intValue) {
        this.baseColor = color;
        this.intValue = intValue;
    }

    //endregion

    //region Getter
    public String GetPreferenceString() {
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

    public MagicColor GetBasecolor() {
        return baseColor;
    }

    public int GetIntValue() {
        return intValue;
    }

    public String GetHexString() {
        return String.format("#%06X", 0xFFFFFF & intValue);
    }

    //endregion

    //region Static members

    public static Color GetDefaultColor(MagicColor color) {
        switch(color) {
            case BLUE:
                return new Color(MagicColor.BLUE, DEFAULT_BLUE);
            case GREEN:
                return new Color(MagicColor.GREEN, DEFAULT_GREEN);
            case RED:
                return new Color(MagicColor.RED, DEFAULT_RED);
            case WHITE:
                return new Color(MagicColor.WHITE, DEFAULT_WHITE);
            default:
                return new Color(MagicColor.BLACK, DEFAULT_BLACK);
        }
    }

    public static int GetDefaultColorInt(MagicColor color) {
        return GetDefaultColor(color).intValue;
    }

    // Get RGB version for int color
    public static int[] GetRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }

    //endregion
}
