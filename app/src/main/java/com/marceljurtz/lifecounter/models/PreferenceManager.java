/*

    Preference Manager for saving, loading and restoring default values

    *
 */

package com.marceljurtz.lifecounter.models;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;

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

    // Set custom color
    public static void saveColor(SharedPreferences preferences, Color color) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(color.getPreferenceString(), color.getIntValue());
        editor.apply();
    }

    // Reset all custom colors
    public static void resetColors(SharedPreferences preferences) {
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.BLACK));
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.BLUE));
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.GREEN));
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.RED));
        saveColor(preferences, Color.getDefaultColor(MagicColorEnum.WHITE));
    }

    // Powersave values
    public static final int powerSave = android.graphics.Color.parseColor("#000000");
    public static final int powerSaveTextcolor = android.graphics.Color.parseColor("#CCC2C0");
    public static final int regularTextcolor = android.graphics.Color.parseColor("#161618");

    // Getter for default colors
    public static int getCustomizedColorOrDefault(MagicColorEnum color, SharedPreferences preferences) {
        switch(color) {
            case BLUE:
                return preferences.getInt(PREF_COLOR_BLUE, Color.DEFAULT_BLUE);
            case GREEN:
                return preferences.getInt(PREF_COLOR_GREEN, Color.DEFAULT_GREEN);
            case RED:
                return preferences.getInt(PREF_COLOR_RED, Color.DEFAULT_RED);
            case WHITE:
                return preferences.getInt(PREF_COLOR_WHITE, Color.DEFAULT_WHITE);
            default:
                return preferences.getInt(PREF_COLOR_BLACK, Color.DEFAULT_BLACK);
        }
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
            editor.apply();
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
        editor.apply();
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
        editor.apply();
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
        editor.apply();
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
    public static void savePlayerCounterData(SharedPreferences preferences, List<Player> players) {
        SharedPreferences.Editor editor = preferences.edit();

        for(Player player : players) {
            editor.putString(player.getPlayerIdEnum().toString(), player.getJson());
        }

        editor.apply();
    }

    public static ArrayList<Player> loadPlayerCounterData(SharedPreferences preferences) {
        ArrayList<Player> players = new ArrayList<Player>();

        Player p1 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.ONE.toString(), null));
        Player p2 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.TWO.toString(), null));
        Player p3 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.THREE.toString(), null));
        Player p4 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.FOUR.toString(), null));

        if(p1 != null) players.add(p1);
        else players.add(new Player(PlayerIdEnum.ONE));
        if(p2 != null) players.add(p2);
        else players.add(new Player(PlayerIdEnum.TWO));
        if(p3 != null) players.add(p3);
        else players.add(new Player(PlayerIdEnum.THREE));
        if(p4 != null) players.add(p4);
        else players.add(new Player(PlayerIdEnum.FOUR));

        return players;
    }

    public static void save2PlayerPointsData(SharedPreferences preferences, Player[] players) {
        SharedPreferences.Editor editor = preferences.edit();

        for(Player player : players) {
            editor.putString(player.getPlayerIdEnum().toString() + "_POINTS2", player.getJson());
        }

        editor.apply();
    }

    public static Player[] load2PlayerPointsData(SharedPreferences preferences) {
        Player[] players = new Player[2];

        Player p1 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.ONE.toString() + "_POINTS2", null));
        Player p2 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.TWO.toString() + "_POINTS2", null));

        if(p1 != null) players[0] = p1;
        else players[0] = new Player(PlayerIdEnum.ONE);
        if(p2 != null) players[1] = p2;
        else players[1] = new Player(PlayerIdEnum.TWO);

        return players;
    }

    public static void save4PlayerPointsData(SharedPreferences preferences, Player[] players) {
        SharedPreferences.Editor editor = preferences.edit();

        for(Player player : players) {
            editor.putString(player.getPlayerIdEnum().toString() + "_POINTS4", player.getJson());
        }

        editor.apply();
    }

    public static Player[] load4PlayerPointsData(SharedPreferences preferences) {
        Player[] players = new Player[4];

        Player p1 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.ONE.toString() + "_POINTS4", null));
        Player p2 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.TWO.toString() + "_POINTS4", null));
        Player p3 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.THREE.toString() + "_POINTS4", null));
        Player p4 = Player.GetInstanceByJson(preferences.getString(PlayerIdEnum.FOUR.toString() + "_POINTS4", null));

        if(p1 != null) players[0] = p1;
        else players[0] = new Player(PlayerIdEnum.ONE);
        if(p2 != null) players[1] = p2;
        else players[1] = new Player(PlayerIdEnum.TWO);
        if(p3 != null) players[2] = p3;
        else players[2] = new Player(PlayerIdEnum.THREE);
        if(p4 != null) players[3] = p4;
        else players[3] = new Player(PlayerIdEnum.FOUR);

        return players;
    }
}
