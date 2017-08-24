package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.graphics.Color;

import com.marceljurtz.lifecounter.Helper.PlayerID;

public class GameController {

    private Context context;
    private GameModel gameModel;
    private GameActivity gameActivity;

    private boolean settingsVisible;
    private boolean poisonVisible;
    private boolean energySavingEnabled;

    private Player player1;
    private Player player2;

    public GameController(GameActivity gameActivity, Context context) {
        this.context = context;
        this.gameActivity = gameActivity;

        settingsVisible = false;
        poisonVisible = false;
        energySavingEnabled = false;

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

    public void setSettingsVisible() {
        settingsVisible = !settingsVisible;
        if(settingsVisible) {
            gameActivity.enableSettingsControls();
        } else {
            gameActivity.disableSettingsControls();
        }
    }

    public boolean getSettingsVisible() {
        return settingsVisible;
    }

    public void setPoisonVisible() {
        poisonVisible = !poisonVisible;
        if(poisonVisible) {
            gameActivity.enablePoisonControls();
        } else {
            gameActivity.disablePoisonControls();
        }
    }

    public boolean getPoisonVisible() {
        return poisonVisible;
    }

    public void setEnergySavingEnabled() {
        energySavingEnabled = !energySavingEnabled;
        if(energySavingEnabled) {
            gameActivity.enableEnergySaving();
        } else {
            gameActivity.disableEnergySaving();
        }
    }

    public boolean getEnergySavingEnabled() {
        return energySavingEnabled;
    }

    public int getBlackInt() {
        return Color.parseColor(getBlack().toString());
    }

    public int getBlueInt() {
        return Color.parseColor(getBlue().toString());
    }

    public int getGreenInt() {
        return Color.parseColor(getGreen().toString());
    }

    public int getRedInt() {
        return Color.parseColor(getRed().toString());
    }

    public int getWhiteInt() {
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
