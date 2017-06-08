package com.marceljurtz.lifecounter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SettingsService {

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
}
