package com.marceljurtz.lifecounter.Game;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class GameModel {

    private SharedPreferences preferences;

    private final int DEFAULT_POISONPOINTS = 0;
    private final int DEFAULT_LIFEPOINTS = 20;
    private int customLifepoints;

    private Player[] players;

    public GameModel(SharedPreferences preferences, Player[] players) {
        this.preferences = preferences;
        this.players = players;

        startGame();
    }

    static int lifepoints = 20;

    public int getColor(String colorString, int nullReturn) {
        return preferences.getInt(colorString, nullReturn);
    }

    public void startGame() {
        int lifepoints = PreferenceManager.getDefaultLifepoints(preferences);
        if(lifepoints == 0) {
            lifepoints = DEFAULT_LIFEPOINTS;
        }
    }

    public void updateLifepoints(PlayerID playerID, ClickType clickType, Operator operator) {

        int amount = PreferenceManager.getDefaultShortclickPoints();
        if(clickType.equals(ClickType.LONG)) amount = PreferenceManager.getLongclickPoints(preferences);

        if(operator.equals(Operator.SUBSTRACT)) amount *= -1;

        switch(playerID) {
            case ONE:
                players[0].updateLifepoints(amount);
                break;
            case TWO:
                players[1].updateLifepoints(amount);
                break;
            case THREE:
                players[2].updateLifepoints(amount);
                break;
            case FOUR:
                players[3].updateLifepoints(amount);
                break;
            default:
                // Nothing to do
                break;
        }
    }

    public void updatePoisonpoints(PlayerID playerID, ClickType clickType, Operator operator) {

        int amount = PreferenceManager.getDefaultShortclickPoints();
        if(clickType.equals(ClickType.LONG)) amount = PreferenceManager.getLongclickPoints(preferences);

        if(operator.equals(Operator.SUBSTRACT)) amount *= -1;

        switch(playerID) {
            case ONE:
                players[0].updatePoisonpoints(amount);
                break;
            case TWO:
                players[1].updatePoisonpoints(amount);
                break;
            case THREE:
                players[2].updatePoisonpoints(amount);
                break;
            case FOUR:
                players[3].updatePoisonpoints(amount);
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
            case THREE:
                return players[2].getLifePoints();
            case FOUR:
                return players[3].getLifePoints();
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
            case THREE:
                return players[2].getPoisonPoints();
            case FOUR:
                return players[3].getPoisonPoints();
            default:
                return 0;
        }
    }
}
