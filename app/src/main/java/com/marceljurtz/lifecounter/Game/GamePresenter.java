package com.marceljurtz.lifecounter.Game;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.Player;
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
    private Player player3;
    private Player player4;

    private final int SCREEN_SMALL = 1;
    private final int SCREEN_NORMAL = 2;
    private final int SCREEN_LARGE = 3;
    private final int SCREEN_XLARGE = 4;

    private boolean hideOtherControlsWhenSettingsDisplayed = false;

    public GamePresenter(GameActivity gameActivity, SharedPreferences preferences) {
        this.preferences = preferences;
        this.gameActivity = gameActivity;

        int screenLayout = gameActivity.GetScreenSize();
        // Configuration.SCREENLAYOUT_SIZE_LARGE;   --> 3
        // Configuration.SCREENLAYOUT_SIZE_NORMAL;  --> 2
        // Configuration.SCREENLAYOUT_SIZE_SMALL;   --> 1
        // Configuration.SCREENLAYOUT_SIZE_XLARGE;  --> 4

        if(screenLayout != SCREEN_XLARGE && gameActivity.GetPlayerAmount() == 4) hideOtherControlsWhenSettingsDisplayed = true;

        player1 = new Player(PlayerID.ONE); // SCHEISSE
        player2 = new Player(PlayerID.TWO);

        if(gameActivity.GetPlayerAmount() == 4) {
            player3 = new Player(PlayerID.THREE);
            player4 = new Player(PlayerID.FOUR);
            gameModel = new GameModel(preferences, new Player[]{player1, player2, player3, player4});
        } else {
            gameModel = new GameModel(preferences, new Player[]{player1, player2});
        }


        // Initiate default colors
        gameActivity.InitColorButton(MagicColor.BLACK, PreferenceManager.getDefaultBlack(preferences));
        gameActivity.InitColorButton(MagicColor.BLUE, PreferenceManager.getDefaultBlue(preferences));
        gameActivity.InitColorButton(MagicColor.GREEN, PreferenceManager.getDefaultGreen(preferences));
        gameActivity.InitColorButton(MagicColor.RED, PreferenceManager.getDefaultRed(preferences));
        gameActivity.InitColorButton(MagicColor.WHITE, PreferenceManager.getDefaultWhite(preferences));

        // Settings, Energy-Saving & Poison
        gameActivity.DisableSettingsControls(hideOtherControlsWhenSettingsDisplayed, false);
        gameActivity.SettingsButtonDisable();
        settingsVisible = false;

        gameActivity.PoisonButtonDisable();
        gameActivity.DisablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        poisonVisible = false;
    }

    @Override
    public void OnCreate() {

    }

    @Override
    public void OnPause() {

    }

    @Override
    public void OnResume() {

        String s = player1.getPlayerIdentification();
        settingsVisible = false;
        gameActivity.DisableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        gameActivity.SettingsButtonDisable();

        poisonVisible = false;
        gameActivity.DisablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        gameActivity.PoisonButtonDisable();

        powerSaveEnabled = false;

        gameActivity.InitColorButton(MagicColor.BLACK, PreferenceManager.getCustomColor(preferences, MagicColor.BLACK));
        gameActivity.InitColorButton(MagicColor.BLUE, PreferenceManager.getCustomColor(preferences, MagicColor.BLUE));
        gameActivity.InitColorButton(MagicColor.GREEN, PreferenceManager.getCustomColor(preferences, MagicColor.GREEN));
        gameActivity.InitColorButton(MagicColor.RED, PreferenceManager.getCustomColor(preferences, MagicColor.RED));
        gameActivity.InitColorButton(MagicColor.WHITE, PreferenceManager.getCustomColor(preferences, MagicColor.WHITE));

        if(PreferenceManager.getScreenTimeoutDisabled(preferences)) {
            gameActivity.DisableScreenTimeout();
        } else {
            gameActivity.EnableScreenTimeout();
        }

        gameActivity.HideNavigationDrawer();
    }

    @Override
    public void OnDestroy() {

    }

    /*
    public void setSettingsVisible() {
        settingsVisible = !settingsVisible;
        if(settingsVisible) {
            gameActivity.EnableSettingsControls();
        } else {
            gameActivity.DisableSettingsControls();
        }
    }

    public boolean getSettingsVisible() {
        return settingsVisible;
    }
*/
    /*public boolean getPoisonVisible() {
        return poisonVisible;
    }*/

    public void togglePowerSavingMode() {
        powerSaveEnabled = !powerSaveEnabled;
        if(powerSaveEnabled) {
            gameActivity.EnableEnergySaving(PreferenceManager.powerSave, PreferenceManager.powerSaveTextcolor);
        } else {
            gameActivity.DisableEnergySaving(PreferenceManager.getDefaultBlack(preferences), PreferenceManager.regularTextcolor);
        }
        gameActivity.SetDrawerTextPowerSaving(!powerSaveEnabled);
    }

    /*

    public boolean getPowerSaveEnabled() {
        return powerSaveEnabled;
    }

    //region Activity Lifecycle functions

    @Override
    public void OnCreate() {

    }

    @Override
    public void OnPause() {

    }

    @Override
    public void OnResume() {

    }

    @Override
    public void OnDestroy() {

    }

    //endregion
*/
    @Override
    public void OnLifeUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        gameModel.updateLifepoints(playerID, clickType, operator);
        int points = gameModel.getPlayerLifepoints(playerID);

        String pointsStr = String.format("%02d",points);
        gameActivity.SetLifepoints(playerID, pointsStr);
    }

    @Override
    public void OnPoisonUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        gameModel.updatePoisonpoints(playerID, clickType, operator);
        int points = gameModel.getPlayerPoisonpoints(playerID);

        String pointsStr = String.format("%02d",points);
        gameActivity.SetPoisonpoints(playerID, pointsStr);
    }



    @Override
    public void OnColorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType) {
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

            gameActivity.SetLayoutColor(playerID, newColor.getIntValue());
        } else if(clickType.equals(ClickType.LONG) && color.equals(MagicColor.BLACK)) {
            togglePowerSavingMode();
        }
    }

    @Override
    public void OnPoisonButtonClick() {
        // Activation only possible on small screens when settings are hidden
        if(!hideOtherControlsWhenSettingsDisplayed || !settingsVisible || gameActivity.GetPlayerAmount() == 2) {
            poisonVisible = !poisonVisible;
            if(poisonVisible) {
                gameActivity.EnablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
                gameActivity.PoisonButtonEnable();
            } else {
                gameActivity.DisablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
                gameActivity.PoisonButtonDisable();
            }
        }
    }

    @Override
    public void OnSettingsButtonClick(ClickType clickType) {
        if(clickType.equals(ClickType.LONG)) {
            gameActivity.LoadSettingsActivity();
        } else {
            settingsVisible = !settingsVisible;
            if(settingsVisible) {
                gameActivity.EnableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
                gameActivity.SettingsButtonEnable();
            } else {
                gameActivity.DisableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
                gameActivity.SettingsButtonDisable();
            }
        }
    }

    @Override
    public void OnResetButtonClick() {
        player1.resetPoints(preferences);
        player2.resetPoints(preferences);

        settingsVisible = false;
        poisonVisible = false;
        gameActivity.DisablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        gameActivity.SettingsButtonDisable();
        gameActivity.DisableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        gameActivity.PoisonButtonDisable();

        gameActivity.SetLifepoints(PlayerID.ONE, String.format("%02d",player1.getLifePoints()));
        gameActivity.SetLifepoints(PlayerID.TWO, String.format("%02d",player2.getLifePoints()));

        gameActivity.SetPoisonpoints(PlayerID.ONE, String.format("%02d",player1.getPoisonPoints()));
        gameActivity.SetPoisonpoints(PlayerID.TWO, String.format("%02d",player2.getPoisonPoints()));

        if(gameActivity.GetPlayerAmount() == 4) {
            player3.resetPoints(preferences);
            player4.resetPoints(preferences);

            gameActivity.SetLifepoints(PlayerID.THREE, String.format("%02d",player3.getLifePoints()));
            gameActivity.SetLifepoints(PlayerID.FOUR, String.format("%02d",player4.getLifePoints()));

            gameActivity.SetPoisonpoints(PlayerID.THREE, String.format("%02d",player3.getPoisonPoints()));
            gameActivity.SetPoisonpoints(PlayerID.FOUR, String.format("%02d",player4.getPoisonPoints()));
        }
    }

    //region NavDrawer Handling
    @Override
    public void OnMenuEntryTogglePlayerTap() {
        if(gameActivity.GetPlayerAmount() == 4) {
            // Load 2 Player View
            PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        } else if(gameActivity.GetPlayerAmount() == 2) {
            // Load 4 Player View
            PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        }
        gameActivity.HideNavigationDrawer();
        gameActivity.RestartActivity();
    }

    @Override
    public void OnMenuEntryDicingTap() {
        gameActivity.LoadDicingActivity();
    }

    @Override
    public void OnMenuEntryEnergySaveTap() {
        togglePowerSavingMode();
    }

    @Override
    public void OnMenuEntrySettingsTap() {
        OnSettingsButtonClick(ClickType.LONG);
    }

    @Override
    public void OnMenuEntryAboutTap() {
        gameActivity.LoadAboutActivity();
    }

    @Override
    public void OnMenuEntryCounterManagerTap() {
        gameActivity.LoadCounterManagerActivity();
    }
    //endregion
}
