package com.marceljurtz.lifecounter.Game;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class GamePresenter implements IGamePresenter {

    private SharedPreferences preferences;
    private GameModel gameModel;
    private IGameView gameActivity;

    private boolean settingsVisible;
    private boolean poisonVisible;
    private boolean powerSaveEnabled;

    private Player player1;
    private Player player2;

    public GamePresenter(GameActivity gameActivity, SharedPreferences preferences) {
        this.preferences = preferences;
        this.gameActivity = gameActivity;

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

    public void togglePowerSavingMode() {
        powerSaveEnabled = !powerSaveEnabled;
        if(powerSaveEnabled) {
            gameActivity.enableEnergySaving(PreferenceManager.powerSave, PreferenceManager.powerSaveTextcolor);
        } else {
            gameActivity.disableEnergySaving(PreferenceManager.getDefaultBlack(preferences), PreferenceManager.regularTextcolor);
        }
    }

    public boolean getPowerSaveEnabled() {
        return powerSaveEnabled;
    }

    //region Activity Lifecycle functions

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        settingsVisible = false;
        gameActivity.disableSettingsControls();

        poisonVisible = false;
        gameActivity.disablePoisonControls();

        powerSaveEnabled = false;

        gameActivity.initColorButton(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK));
        gameActivity.initColorButton(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE));
        gameActivity.initColorButton(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN));
        gameActivity.initColorButton(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED));
        gameActivity.initColorButton(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE));
    }

    @Override
    public void onDestroy() {

    }

    //endregion

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
    public void onColorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType) {
        if(clickType.equals(ClickType.SHORT)) {

            // Disable PowerSaveMode if enabled
            if(powerSaveEnabled) togglePowerSavingMode();

            Color newColor;

            switch (color) {
                case BLUE:
                    newColor = new Color(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE));
                    break;
                case GREEN:
                    newColor = new Color(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN));
                    break;
                case RED:
                    newColor = new Color(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED));
                    break;
                case WHITE:
                    newColor = new Color(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE));
                    break;
                default:
                    newColor = new Color(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK));
                    break;
            }

            gameActivity.setLayoutColor(playerID, newColor.getIntValue());
        } else if(clickType.equals(ClickType.LONG) && color.equals(MagicColor.BLACK)) {
            togglePowerSavingMode();
        }
    }

    @Override
    public void onPoisonButtonClick() {
        poisonVisible = !poisonVisible;
        if(poisonVisible) {
            gameActivity.enablePoisonControls();
            gameActivity.poisonButtonEnable();
        } else {
            gameActivity.disablePoisonControls();
            gameActivity.poisonButtonDisable();
        }
    }

    @Override
    public void onSettingsButtonClick(ClickType clickType) {
        if(clickType.equals(ClickType.LONG)) {
            gameActivity.loadSettingsActivity();
        } else {
            settingsVisible = !settingsVisible;
            if(settingsVisible) {
                gameActivity.enableSettingsControls();
                gameActivity.settingsButtonEnable();
            } else {
                gameActivity.disableSettingsControls();
                gameActivity.settingsButtonDisable();
            }
        }
    }

    @Override
    public void onResetButtonClick() {
        player1.resetPoints(preferences);
        player2.resetPoints(preferences);

        settingsVisible = false;
        poisonVisible = false;
        gameActivity.disablePoisonControls();
        gameActivity.settingsButtonDisable();
        gameActivity.disableSettingsControls();
        gameActivity.poisonButtonDisable();

        gameActivity.setLifepoints(PlayerID.ONE, String.format("%02d",player1.getLifePoints()));
        gameActivity.setLifepoints(PlayerID.TWO, String.format("%02d",player2.getLifePoints()));

        gameActivity.setPoisonpoints(PlayerID.ONE, String.format("%02d",player1.getPoisonPoints()));
        gameActivity.setPoisonpoints(PlayerID.TWO, String.format("%02d",player2.getPoisonPoints()));
    }
}