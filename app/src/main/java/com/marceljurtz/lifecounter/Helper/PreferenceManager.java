package com.marceljurtz.lifecounter.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.R;

public class PreferenceManager {

    private static final int DEFAULT_LIFEPOINTS = 0;

    public static int getColor(Context context, MagicColor color) {
        SharedPreferences sPrefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sPrefs.getInt(colorString, Color.getDefaultColor(color));
    }

    public static void setColor(Context context, MagicColor settingsColor, Color newColor) {

    }

    public static int getDefaultLifepoints(Context context) {
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sprefs.getInt(context.getString(R.string.shared_preferences_lifepoints), DEFAULT_LIFEPOINTS);
    }



    // DEFAULT MAX AND MIN VALUES
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


    // DEFAULT COLORS
    public static final int powerSave = android.graphics.Color.parseColor("#000000");
    public static final int powerSaveTextcolor = android.graphics.Color.parseColor("#CCC2C0");
    public static final int regularTextcolor = android.graphics.Color.parseColor("#161618");

    public static int getDefaultBlack(Context context){
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        int black = sprefs.getInt(context.getString(R.string.shared_preferences_color_black), Color.DEFAULT_BLACK);
        return black;
    }

    public static int getDefaultBlue(Context context){
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        int blue = sprefs.getInt(context.getString(R.string.shared_preferences_color_blue), Color.DEFAULT_BLUE);
        return blue;
    }

    public static int getDefaultGreen(Context context){
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        int green = sprefs.getInt(context.getString(R.string.shared_preferences_color_green), Color.DEFAULT_GREEN);
        return green;
    }

    public static int getDefaultRed(Context context{
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        int red = sprefs.getInt(context.getString(R.string.shared_preferences_color_red), Color.DEFAULT_RED);
        return red;
    }

    public static int getDefaultWhite(Context context){
        SharedPreferences sprefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        int white = sprefs.getInt(context.getString(R.string.shared_preferences_color_white), Color.DEFAULT_WHITE);
        return white;
    }

    public static int[] getRGB(int color) {
        int[] rgb = new int[3];
        rgb[0] = (color >> 16) & 0xFF;
        rgb[1] = (color >> 8) & 0xFF;
        rgb[2] = (color >> 0) & 0xFF;
        return rgb;
    }

    public static String getHexString(int color) {
        return String.format("#%06X", 0xFFFFFF & color);
    }
}
