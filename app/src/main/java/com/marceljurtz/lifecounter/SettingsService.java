package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingsService {

    private static final int DEFAULT_LIFEPOINTS = 20;

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
}
