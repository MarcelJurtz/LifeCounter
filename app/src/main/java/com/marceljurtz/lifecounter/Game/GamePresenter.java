package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class GamePresenter implements IPresenter {

    private SharedPreferences preferences;
    private GameModel gameModel;
    private GameActivity gameActivity;

    private boolean settingsVisible;
    private boolean poisonVisible;
    private boolean energySavingEnabled;

    private Player player1;
    private Player player2;

    public GamePresenter(GameActivity gameActivity, SharedPreferences preferences) {
        this.preferences = preferences;
        this.gameActivity = gameActivity;

        settingsVisible = false;
        poisonVisible = false;
        energySavingEnabled = false;

        player1 = new Player(PlayerID.ONE);
        player2 = new Player(PlayerID.TWO);

        gameModel = new GameModel(preferences, new Player[]{player1, player2});

        // Initiate default colors
        gameActivity.initColorButton(MagicColor.BLACK, PreferenceManager.getDefaultBlack(preferences));
        gameActivity.initColorButton(MagicColor.BLUE, PreferenceManager.getDefaultBlue(preferences));
        gameActivity.initColorButton(MagicColor.GREEN, PreferenceManager.getDefaultGreen(preferences));
        gameActivity.initColorButton(MagicColor.RED, PreferenceManager.getDefaultRed(preferences));
        gameActivity.initColorButton(MagicColor.WHITE, PreferenceManager.getDefaultWhite(preferences));
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
            gameActivity.enableEnergySaving(PreferenceManager.powerSave, PreferenceManager.powerSaveTextcolor);
        } else {
            gameActivity.disableEnergySaving(PreferenceManager.getDefaultBlack(preferences), PreferenceManager.regularTextcolor);
        }
    }

    public boolean getEnergySavingEnabled() {
        return energySavingEnabled;
    }

    @Override
    public void onCreate(IView view) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onLifeUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        gameModel.updateLifepoints(playerID, clickType, operator);
        int points = gameModel.getPlayerLifepoints(playerID);

        String pointsStr = String.format("%02d",points);
        gameActivity.setLifepoints(playerID, pointsStr);
    }

    @Override
    public void onPoisonUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        gameModel.updatePoisonpoints(playerID, clickType, operator);
        int points = gameModel.getPlayerPoisonpoints(playerID);

        String pointsStr = String.format("%02d",points);
        gameActivity.setPoisonpoints(playerID, pointsStr);
    }

    @Override
    public void colorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType) {
        if(clickType.equals(ClickType.SHORT)) {
            int newColor = com.marceljurtz.lifecounter.Helper.Color.getDefaultColorInt(color);
            gameActivity.setLayoutColor(playerID, newColor);
        }
    }

    @Override
    public void poisonButtonClick() {
        poisonVisible = !poisonVisible;
        if(poisonVisible) {
            gameActivity.enablePoisonControls();
        } else {
            gameActivity.disablePoisonControls();
        }
    }

    @Override
    public void settingsButtonClick(ClickType clickType) {
        if(clickType.equals(ClickType.LONG)) {
            gameActivity.loadSettingsActivity();
        }
    }

    @Override
    public void resetButtonClick() {

    }
}
