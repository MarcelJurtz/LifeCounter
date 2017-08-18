package com.marceljurtz.lifecounter.Game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;

public class GameModel {

    private final Context context;
    private final SharedPreferences sPrefs;

    private final int DEFAULT_POISONPOINTS = 0;
    private final int DEFAULT_LIFEPOINTS = 20;
    private int customLifepoints;

    private Player[] players;

    public GameModel(Context context, Player[] players) {
        this.context = context;
        sPrefs = context.getSharedPreferences(context.getString(R.string.shared_preferences_name), Activity.MODE_PRIVATE);
        this.players = players;


        startGame();
    }

    static int lifepoints = 20;

    public int getColor(String colorString, int nullReturn) {
        return sPrefs.getInt(colorString, nullReturn);
    }

    public void startGame() {
        int lifepoints = PreferenceManager.getDefaultLifepoints(context);
        if(lifepoints == 0) {
            lifepoints = DEFAULT_LIFEPOINTS;
        }

        for (Player p: players) {
            p.setDefaults(lifepoints, DEFAULT_POISONPOINTS);
        }
    }
}
