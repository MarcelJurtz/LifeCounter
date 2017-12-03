/*

    Preference Manager for saving, loading and restoring default values

    *
 */

package com.marceljurtz.lifecounter.Helper;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class PreferenceManager {

    //region Default values

    private static final int DEFAULT_LIFEPOINTS = 20;
    private static final int DEFAULT_LONGCLICK_POINTS = 5;
    private static final int DEFAULT_SHORTCLICK_POINTS = 1;

    private static final int DEFAULT_PLAYER_AMOUNT = 2;

    private static final boolean DEFAULT_KEEP_SCREEN_ON = true;

    // MAX and MIN Values
    private static final int MAX_POISON = 25;
    private static final int MIN_POISON = 0;
    private static final int MAX_LIFE = 1000;
    private static final int MIN_LIFE = -100;

    //endregion

    //region Preference strings

    public static final String PREFS = "MTG_SETTINGS";
    public static final String PREF_COLOR_BLACK = "COLOR_BLACK";
    public static final String PREF_COLOR_BLUE = "COLOR_BLUE";
    public static final String PREF_COLOR_GREEN = "COLOR_GREEN";
    public static final String PREF_COLOR_RED = "COLOR_RED";
    public static final String PREF_COLOR_WHITE = "COLOR_WHITE";

    private static final String PREF_LONG_CLICK_POINTS = "DEFAULT_LONG_CLICK_POINTS";
    private static final String PREF_LIFEPOINTS = "DEFAULT_LIFEPOINTS";

    private static final String PREF_PLAYER_AMOUNT = "PLAYER_AMOUNT";

    private static final String PREF_KEEP_SCREEN_ON = "KEEP_SCREEN_ON";

    //endregion

    //region Custom colors

    // Get custom color
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

    // Set custom color
    public static void saveColor(SharedPreferences preferences, Color color) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(color.getPreferenceString(), color.getIntValue());
        editor.commit();
    }

    // Reset all custom colors
    public static void resetColors(SharedPreferences preferences) {
        saveColor(preferences, new Color(MagicColor.BLACK));
        saveColor(preferences, new Color(MagicColor.BLUE));
        saveColor(preferences, new Color(MagicColor.GREEN));
        saveColor(preferences, new Color(MagicColor.RED));
        saveColor(preferences, new Color(MagicColor.WHITE));
    }

    // Powersave values
    public static final int powerSave = android.graphics.Color.parseColor("#000000");
    public static final int powerSaveTextcolor = android.graphics.Color.parseColor("#CCC2C0");
    public static final int regularTextcolor = android.graphics.Color.parseColor("#161618");

    // Getter for default colors
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

    //endregion

    //region Amount of Players
    public static int getPlayerAmount(SharedPreferences preferences) {
        return preferences.getInt(PREF_PLAYER_AMOUNT, DEFAULT_PLAYER_AMOUNT);
    }

    public static void saveDefaultPlayerAmount(SharedPreferences preferences, int playeramount) {
        if(playeramount == 2 || playeramount == 4) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(PREF_PLAYER_AMOUNT, playeramount);
            editor.commit();
        }
    }
    //endregion

    //region Keep Screen On

    public static boolean getScreenTimeoutDisabled(SharedPreferences preferences) {
        return preferences.getBoolean(PREF_KEEP_SCREEN_ON, DEFAULT_KEEP_SCREEN_ON);
    }

    public static void saveScreenTimeoutDisabled(SharedPreferences preferences, boolean timeoutDisabled) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_KEEP_SCREEN_ON, timeoutDisabled);
        editor.commit();
    }

    //endregion

    //region Lifepoints and Longclickpoints

    // Get Lifepoints
    public static int getDefaultLifepoints(SharedPreferences preferences) {
        return preferences.getInt(PREF_LIFEPOINTS, DEFAULT_LIFEPOINTS);
    }

    // Set Lifepoints
    public static void saveDefaultLifepoints(SharedPreferences preferences, int lifepoints) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_LIFEPOINTS, lifepoints);
        editor.commit();
    }

    // Reset Lifepoints
    public static void resetLifepoints(SharedPreferences preferences) {
        saveDefaultLifepoints(preferences, DEFAULT_LIFEPOINTS);
    }

    // Get Shortclickpoints
    public static int getDefaultShortclickPoints() {
        return DEFAULT_SHORTCLICK_POINTS;
    }

    // Get Longclickpoints
    public static int getLongclickPoints(SharedPreferences preferences) {
        int points = preferences.getInt(PREF_LONG_CLICK_POINTS, DEFAULT_LONGCLICK_POINTS);
        return points;
    }

    // Set Longclickpoints
    public static void saveDefaultLongClickPoints(SharedPreferences preferences, int longClickPoints) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(PREF_LONG_CLICK_POINTS, longClickPoints);
        editor.commit();
    }

    // Reset Longclickpoints
    public static void resetLongClickPoints(SharedPreferences preferences) {
        saveDefaultLongClickPoints(preferences, DEFAULT_LONGCLICK_POINTS);
    }

    //endregion

    //region Min / Max values

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

    //region Player Data (points + counters)
    public static void SavePlayerData(SharedPreferences preferences, List<Player> players) {
        SharedPreferences.Editor editor = preferences.edit();

        for(Player player : players) {
            editor.putString(player.getPlayerID().toString(), player.GetJson());
        }

        editor.commit();
    }

    public static ArrayList<Player> LoadPlayerData(SharedPreferences preferences) {
        ArrayList<Player> players = new ArrayList<Player>();

        Player p1 = Player.GetInstanceByJson(preferences.getString(PlayerID.ONE.toString(), null));
        Player p2 = Player.GetInstanceByJson(preferences.getString(PlayerID.TWO.toString(), null));
        Player p3 = Player.GetInstanceByJson(preferences.getString(PlayerID.THREE.toString(), null));
        Player p4 = Player.GetInstanceByJson(preferences.getString(PlayerID.FOUR.toString(), null));

        if(p1 != null) players.add(p1);
        else players.add(new Player(PlayerID.ONE));
        if(p2 != null) players.add(p2);
        else players.add(new Player(PlayerID.TWO));
        if(p3 != null) players.add(p3);
        else players.add(new Player(PlayerID.THREE));
        if(p4 != null) players.add(p4);
        else players.add(new Player(PlayerID.FOUR));

        return players;
    }
}
