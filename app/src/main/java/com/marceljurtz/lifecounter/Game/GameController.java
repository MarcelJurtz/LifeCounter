package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.graphics.Color;

import com.marceljurtz.lifecounter.Helper.PlayerID;

public class GameController {

    private Context context;
    private GameModel gameModel;
    private GameActivity gameActivity;

    private Player player1;
    private Player player2;

    public GameController(GameActivity gameActivity, Context context) {
        this.context = context;
        this.gameActivity = gameActivity;

        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);

        gameModel = new GameModel(context, new Player[]{player1, player2});

        // Initiate default colors
        gameActivity.initColorButtonBlack(getBlackInt());
        gameActivity.initColorButtonBlue(getBlueInt());
        gameActivity.initColorButtonGreen(getGreenInt());
        gameActivity.initColorButtonRed(getRedInt());
        gameActivity.initColorButtonWhite(getWhiteInt());
    }

    private int getBlackInt() {
        return Color.parseColor(getBlack().toString());
    }

    private int getBlueInt() {
        return Color.parseColor(getBlue().toString());
    }

    private int getGreenInt() {
        return Color.parseColor(getGreen().toString());
    }

    private int getRedInt() {
        return Color.parseColor(getRed().toString());
    }

    private int getWhiteInt() {
        return Color.parseColor(getWhite().toString());
    }

    // Increase or decrease lifepoints
    public void updateLifepoints(PlayerID id, int amount) {
        if(id.equals(player1.getPlayerID())) {
            player1.updateLifepoints(amount);
            gameActivity.setLifepoints(id, player1.getLifePoints());
        } else if(id.equals(player2.getPlayerID())) {
            player2.updateLifepoints(amount);
            gameActivity.setLifepoints(id, player2.getLifePoints());
        }
    }

    // Increase or decrease poison points
    public void updatePoisonpoints(PlayerID id, int amount) {
        if(id.equals(player1.getPlayerID())) {
            player1.updateLifepoints(amount);
            gameActivity.setPoisonpoints(id, player1.getPoisonPoints());
        } else if(id.equals(player2.getPlayerID())) {
            player2.updateLifepoints(amount);
            gameActivity.setPoisonpoints(id, player2.getPoisonPoints());
        }
    }
}
