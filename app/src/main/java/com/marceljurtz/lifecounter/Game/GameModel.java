package com.marceljurtz.lifecounter.Game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.R;

import static com.marceljurtz.lifecounter.Helper.PlayerID.TWO;

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

    public void updateLifepoints(PlayerID playerID, ClickType clickType, Operator operator) {

        int amount = PreferenceManager.SHORTCLICK_POINTS;
        if(clickType.equals(ClickType.LONG)) amount = PreferenceManager.LONGCLICK_POINTS;

        if(operator.equals(Operator.SUBSTRACT)) amount *= -1;

        switch(playerID) {
            case ONE:
                players[0].updateLifepoints(amount);
                break;
            case TWO:
                players[1].updateLifepoints(amount);
                break;
            default:
                // Nothing to do
                break;
        }
    }

    public void updatePoisonpoints(PlayerID playerID, ClickType clickType, Operator operator) {

        int amount = PreferenceManager.SHORTCLICK_POINTS;
        if(clickType.equals(ClickType.LONG)) amount = PreferenceManager.LONGCLICK_POINTS;

        if(operator.equals(Operator.SUBSTRACT)) amount *= -1;

        switch(playerID) {
            case ONE:
                players[0].updatePoisonpoints(amount);
                break;
            case TWO:
                players[1].updatePoisonpoints(amount);
                break;
            default:
                // Nothing to do
                break;
        }
    }

    public int getPlayerLifepoints(PlayerID playerID) {
        switch(playerID) {
            case ONE :
                return players[0].getLifePoints();
            case TWO:
                return players[1].getLifePoints();
            default:
                return 0;
        }
    }

    public int getPlayerPoisonpoints(PlayerID playerID) {
        switch(playerID) {
            case ONE :
                return players[0].getPoisonPoints();
            case TWO:
                return players[1].getPoisonPoints();
            default:
                return 0;
        }
    }
}
