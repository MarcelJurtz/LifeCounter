package com.marceljurtz.lifecounter.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.R;

public class SettingsService {

    private static final int DEFAULT_LIFEPOINTS = 20;
    private static final int DEFAULT_LONG_CLICK_POINTS = 5;

    public static int getColor(Context context, String colorString, int nullReturn) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sp.getInt(colorString, nullReturn);
    }

    public static void saveColor(Context context, String colorString, int colorValue) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(colorString, colorValue);
        editor.commit();
    }

    public static void setLifepoints(Context context, int lifepoints) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(context.getString(R.string.shared_preferences_lifepoints), lifepoints);
        editor.commit();
    }

    public static int getLifepoints(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sp.getInt(context.getString(R.string.shared_preferences_lifepoints), DEFAULT_LIFEPOINTS);
    }

    public static void resetLifepoints(Context context) {
        setLifepoints(context,DEFAULT_LIFEPOINTS);
    }

    public static int getDefaultPoisonPoints() {
        return 0;
    }

    public static int getLongClickPoints(Context context) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sp.getInt(context.getString(R.string.shared_preferences_long_click_points), DEFAULT_LONG_CLICK_POINTS);
    }

    public static void setLongClickPoints(Context context, int points) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(context.getString(R.string.shared_preferences_long_click_points), points);
        editor.commit();
    }

    public static void resetLongClickPoints(Context context) {
        setLongClickPoints(context, DEFAULT_LONG_CLICK_POINTS);
    }
}
