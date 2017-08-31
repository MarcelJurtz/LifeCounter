package com.marceljurtz.lifecounter.Helper;

import android.app.Activity;
import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.R;

public class PreferenceManager {

    // Defaults for reset
    private static final int DEFAULT_LIFEPOINTS = 20;
    private static final int DEFAULT_LONG_CLICK_POINTS = 5;

    //region Preference Strings

    public static final String PREFS = "MTG_SETTINGS";
    private static final String PREF_COLOR_BLACK = "COLOR_BLACK";
    private static final String PREF_COLOR_BLUE = "COLOR_BLUE";
    private static final String PREF_COLOR_GREEN = "COLOR_GREEN";
    private static final String PREF_COLOR_RED = "COLOR_RED";
    private static final String PREF_COLOR_WHITE = "COLOR_WHITE";

    private static final String PREF_LONG_CLICK_POINTS = "DEFAULT_LONG_CLICK_POINTS";
    private static final String PREF_LIFEPOINTS = "DEFAULT_LIFEPOINTS";


    //endregion

    public static int getCustomColor(SharedPreferences preferences, MagicColor color) {
        String colorString;

        switch(color) {
            case BLUE:
                colorString = PREF_COLOR_BLUE;
                break;
            case GREEN:
                colorString = PREF_COLOR_GREEN;
                break;
            case RED:
                colorString = PREF_COLOR_RED;
                break;
            case WHITE:
                colorString = PREF_COLOR_WHITE;
                break;
            default:
                colorString = PREF_COLOR_BLACK;
        }

        return preferences.getInt(colorString, Color.getDefaultColorInt(color));
    }

    public static void saveColor(SharedPreferences preferences, MagicColor settingsColor, Color newColor) {

    }

    public static int getDefaultLifepoints(SharedPreferences preferences) {
        return preferences.getInt(PREF_LIFEPOINTS, DEFAULT_LIFEPOINTS);
    }


    //region Long and Short Click Point Amounts
    private static final int LONGCLICK_POINTS = 5;
    private static final int SHORTCLICK_POINTS = 1;

    public static int getShortclickPoints() {
        return SHORTCLICK_POINTS;
    }

    public static int getLongclickPoints(SharedPreferences preferences) {
        int points = preferences.getInt(PREF_LONG_CLICK_POINTS, LONGCLICK_POINTS);
        return points;
    }
    //endregion

    //region MAX and MIN Values
    private static final int MAX_POISON = 25;
    private static final int MIN_POISON = 0;
    private static final int MAX_LIFE = 1000;
    private static final int MIN_LIFE = -100;

    public static int getMaxPoison(){
        return MAX_POISON;
    }

    public static int getMinPoison(){
        return MIN_POISON;
    }

    public static int getMaxLife(){
        return MAX_LIFE;
    }

    public static int getMinLife() {
        return MIN_LIFE;
    }
    //endregion

    //region Reset functionality

    public static void resetLifepoints(SharedPreferences preferences) {

    }

    public static void resetLongClickPoints(SharedPreferences preferences) {

    }

    //endregion

    //region Colors
    public static final int powerSave = android.graphics.Color.parseColor("#000000");
    public static final int powerSaveTextcolor = android.graphics.Color.parseColor("#CCC2C0");
    public static final int regularTextcolor = android.graphics.Color.parseColor("#161618");

    public static int getDefaultBlack(SharedPreferences preferences){
        int black = preferences.getInt(PREF_COLOR_BLACK, Color.DEFAULT_BLACK);
        return black;
    }

    public static int getDefaultBlue(SharedPreferences preferences){
        int blue = preferences.getInt(PREF_COLOR_BLUE, Color.DEFAULT_BLUE);
        return blue;
    }

    public static int getDefaultGreen(SharedPreferences preferences){
        int green = preferences.getInt(PREF_COLOR_GREEN, Color.DEFAULT_GREEN);
        return green;
    }

    public static int getDefaultRed(SharedPreferences preferences) {
        int red = preferences.getInt(PREF_COLOR_RED, Color.DEFAULT_RED);
        return red;
    }

    public static int getDefaultWhite(SharedPreferences preferences){
        int white = preferences.getInt(PREF_COLOR_WHITE, Color.DEFAULT_WHITE);
        return white;
    }

    public static int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }
    //endregion
}
