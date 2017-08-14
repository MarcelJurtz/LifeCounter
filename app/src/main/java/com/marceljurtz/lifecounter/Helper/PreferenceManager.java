package com.marceljurtz.lifecounter.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.R;

public class PreferenceManager {
    public static int getColor(Context context, MagicColor color) {
        SharedPreferences sPrefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        return sPrefs.getInt(colorString, Color.getDefaultColor(color));
    }

    public static void setColor(Context context, MagicColor settingsColor, Color newColor) {

    }
}
