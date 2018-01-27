/*
Custom Color Manager for Magic Lifecounter

* MagicColor for identification of original color
* Int-Value for setting backgroundcolors and stuff
* Int-Value can be customized and is saved via SharedPreferences
* For this, use the prefString-member
 */

package com.marceljurtz.lifecounter.Helper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
    private String prefString;

    public Color(MagicColor baseColor, int intValue) {
        this.intValue = intValue;
        this.baseColor = baseColor;

        switch(baseColor) {
            case BLUE:
                prefString = PreferenceManager.PREF_COLOR_BLUE;
                break;
            case GREEN:
                prefString = PreferenceManager.PREF_COLOR_GREEN;
                break;
            case RED:
                prefString = PreferenceManager.PREF_COLOR_RED;
                break;
            case WHITE:
                prefString = PreferenceManager.PREF_COLOR_WHITE;
                break;
            default:
                prefString = PreferenceManager.PREF_COLOR_BLACK;
        }
    }


    public Color(MagicColor baseColor) {
        this(baseColor, getDefaultColorInt(baseColor));
    }

    //endregion

    //region Getter

    public String GetPreferenceString() {
        return this.prefString;
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

    public static Color getDefaultColor(MagicColor color) {
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

    public static int getDefaultColorInt(MagicColor color) {
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

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(baseColor.name());
//    }
//
//    private Color(Parcel source) {
//        baseColor = MagicColor.valueOf(source.readString());
//    }
//
//    public static final Parcelable.Creator<Color> CREATOR
//            = new Parcelable.Creator<Color>() {
//        @Override
//        public Color createFromParcel(Parcel in) {
//            return new Color(in);
//        }
//
//        @Override
//        public Color[] newArray(int size) {
//            return new Color[size];
//        }
//    };
}
