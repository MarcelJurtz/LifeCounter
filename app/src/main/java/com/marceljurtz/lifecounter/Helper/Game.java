package com.marceljurtz.lifecounter.Helper;

import android.content.SharedPreferences;

public class Game {

    private SharedPreferences preferences;

    private final int DEFAULT_POISONPOINTS = 0;
    private final int DEFAULT_LIFEPOINTS = 20;
    private int customLifepoints;

    private Player[] players;

    public Game(SharedPreferences preferences, Player[] players) {
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
                players[0].UpdateLifepoints(amount);
                break;
            case TWO:
                players[1].UpdateLifepoints(amount);
                break;
            case THREE:
                players[2].UpdateLifepoints(amount);
                break;
            case FOUR:
                players[3].UpdateLifepoints(amount);
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
                players[0].UpdatePoisonpoints(amount);
                break;
            case TWO:
                players[1].UpdatePoisonpoints(amount);
                break;
            case THREE:
                players[2].UpdatePoisonpoints(amount);
                break;
            case FOUR:
                players[3].UpdatePoisonpoints(amount);
                break;
            default:
                // Nothing to do
                break;
        }
    }

    public int getPlayerLifepoints(PlayerID playerID) {
        switch(playerID) {
            case ONE :
                return players[0].GetLifePoints();
            case TWO:
                return players[1].GetLifePoints();
            case THREE:
                return players[2].GetLifePoints();
            case FOUR:
                return players[3].GetLifePoints();
            default:
                return 0;
        }
    }

    public int getPlayerPoisonpoints(PlayerID playerID) {
        switch(playerID) {
            case ONE :
                return players[0].GetPoisonPoints();
            case TWO:
                return players[1].GetPoisonPoints();
            case THREE:
                return players[2].GetPoisonPoints();
            case FOUR:
                return players[3].GetPoisonPoints();
            default:
                return 0;
        }
    }
}
