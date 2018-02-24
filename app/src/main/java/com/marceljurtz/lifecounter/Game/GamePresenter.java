package com.marceljurtz.lifecounter.Game;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.About.AboutActivity;
import com.marceljurtz.lifecounter.Counter.CounterActivity;
import com.marceljurtz.lifecounter.Dicing.DicingActivity;
import com.marceljurtz.lifecounter.Helper.ClickType;
import com.marceljurtz.lifecounter.Helper.Color;
import com.marceljurtz.lifecounter.Helper.Game;
import com.marceljurtz.lifecounter.Helper.MagicColor;
import com.marceljurtz.lifecounter.Helper.Operator;
import com.marceljurtz.lifecounter.Helper.Player;
import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;
import com.marceljurtz.lifecounter.Settings.SettingsActivity;

public class GamePresenter implements IGamePresenter {

    private SharedPreferences preferences;
    private Game game;
    private IGameView view;

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

    public GamePresenter(IGameView view, SharedPreferences preferences) {
        this.preferences = preferences;
        this.view = view;

        int screenLayout = view.getScreenSize();
        // Configuration.SCREENLAYOUT_SIZE_LARGE;   --> 3
        // Configuration.SCREENLAYOUT_SIZE_NORMAL;  --> 2
        // Configuration.SCREENLAYOUT_SIZE_SMALL;   --> 1
        // Configuration.SCREENLAYOUT_SIZE_XLARGE;  --> 4

        if(screenLayout != SCREEN_XLARGE && view.getPlayerAmount() == 4) hideOtherControlsWhenSettingsDisplayed = true;

        player1 = new Player(PlayerID.ONE); // SCHEISSE
        player2 = new Player(PlayerID.TWO);

        if(view.getPlayerAmount() == 4) {
            player3 = new Player(PlayerID.THREE);
            player4 = new Player(PlayerID.FOUR);
            game = new Game(preferences, new Player[]{player1, player2, player3, player4});
        } else {
            game = new Game(preferences, new Player[]{player1, player2});
        }


        // Initiate default colors
        view.initColorButton(MagicColor.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLACK, preferences));
        view.initColorButton(MagicColor.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLUE, preferences));
        view.initColorButton(MagicColor.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColor.GREEN, preferences));
        view.initColorButton(MagicColor.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColor.RED, preferences));
        view.initColorButton(MagicColor.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.WHITE, preferences));

        // Settings, Energy-Saving & Poison
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, false);
        view.settingsButtonDisable();
        settingsVisible = false;

        view.poisonButtonDisable();
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        poisonVisible = false;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {
        game.saveGameState(preferences);
    }

    @Override
    public void onResume() {

        if(view.getPlayerAmount() == 4) {
            game.loadGameState(preferences, 4);
        } else {
            game.loadGameState(preferences, 2);
        }

        for(Player player : game.getPlayers()) {
            player.setColor(new Color(player.getColorOrDefault().getBasecolor(), PreferenceManager.getCustomizedColorOrDefault(player.getColorOrDefault().getBasecolor(), preferences)));
            view.setLifepoints(player.getPlayerID(), String.format("%02d",player.getLifePoints()));
            view.setPoisonpoints(player.getPlayerID(), String.format("%02d", player.getPoisonPoints()));
            view.setLayoutColor(player.getPlayerID(), player.getColorOrDefault().getIntValue());
        }

        settingsVisible = false;
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        view.settingsButtonDisable();

        poisonVisible = false;
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        view.poisonButtonDisable();

        powerSaveEnabled = false;

        view.initColorButton(MagicColor.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLACK, preferences));
        view.initColorButton(MagicColor.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLUE, preferences));
        view.initColorButton(MagicColor.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColor.GREEN, preferences));
        view.initColorButton(MagicColor.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColor.RED, preferences));
        view.initColorButton(MagicColor.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.WHITE, preferences));

        if(PreferenceManager.getScreenTimeoutDisabled(preferences)) {
            view.disableScreenTimeout();
        } else {
            view.enableScreenTimeout();
        }

        view.hideNavigationDrawer();
    }

    @Override
    public void onDestroy() {

    }

    public void togglePowerSavingMode() {
        powerSaveEnabled = !powerSaveEnabled;
        if(powerSaveEnabled) {
            view.enableEnergySaving(PreferenceManager.powerSave, PreferenceManager.powerSaveTextcolor);
        } else {
            view.disableEnergySaving(PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLACK, preferences), PreferenceManager.regularTextcolor);
        }
        view.setDrawerTextPowerSaving(!powerSaveEnabled);
    }

    @Override
    public void onLifeUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        game.updateLifepoints(playerID, clickType, operator);
        int points = game.getPlayerLifepoints(playerID);

        String pointsStr = String.format("%02d",points);
        view.setLifepoints(playerID, pointsStr);
    }

    @Override
    public void onPoisonUpdate(PlayerID playerID, ClickType clickType, Operator operator) {
        game.updatePoisonpoints(playerID, clickType, operator);
        int points = game.getPlayerPoisonpoints(playerID);

        String pointsStr = String.format("%02d",points);
        view.setPoisonpoints(playerID, pointsStr);
    }

    @Override
    public void onColorButtonClick(PlayerID playerID, MagicColor color, ClickType clickType) {
        if(clickType.equals(ClickType.SHORT)) {

            // Disable PowerSaveMode if enabled
            if(powerSaveEnabled) togglePowerSavingMode();

            Color newColor;

            switch (color) {
                case BLUE:
                    newColor = new Color(MagicColor.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLUE, preferences));
                    break;
                case GREEN:
                    newColor = new Color(MagicColor.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColor.GREEN, preferences));
                    break;
                case RED:
                    newColor = new Color(MagicColor.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColor.RED, preferences));
                    break;
                case WHITE:
                    newColor = new Color(MagicColor.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColor.WHITE, preferences));
                    break;
                default:
                    newColor = new Color(MagicColor.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColor.BLACK, preferences));
                    break;
            }

            switch(playerID) {
                case ONE:
                    player1.setColor(newColor);
                    game.getPlayers()[0].setColor(newColor);
                    break;
                case TWO:
                    player2.setColor(newColor);
                    game.getPlayers()[1].setColor(newColor);
                    break;
                case THREE:
                    player3.setColor(newColor);
                    game.getPlayers()[2].setColor(newColor);
                    break;
                case FOUR:
                    player4.setColor(newColor);
                    game.getPlayers()[3].setColor(newColor);
                    break;
                default:
            }

            view.setLayoutColor(playerID, newColor.getIntValue());
        } else if(clickType.equals(ClickType.LONG) && color.equals(MagicColor.BLACK)) {
            togglePowerSavingMode();
        }
    }

    @Override
    public void onPoisonButtonClick() {
        // Activation only possible on small screens when settings are hidden
        if(!hideOtherControlsWhenSettingsDisplayed || !settingsVisible || view.getPlayerAmount() == 2) {
            poisonVisible = !poisonVisible;
            if(poisonVisible) {
                view.enablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
                view.poisonButtonEnable();
            } else {
                view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
                view.poisonButtonDisable();
            }
        }
    }

    @Override
    public void onSettingsButtonClick(ClickType clickType) {
        if(clickType.equals(ClickType.LONG)) {
            view.loadActivity(SettingsActivity.class);
        } else {
            settingsVisible = !settingsVisible;
            if(settingsVisible) {
                view.enableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible && hideOtherControlsWhenSettingsDisplayed);
                view.settingsButtonEnable();
            } else {
                view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible && hideOtherControlsWhenSettingsDisplayed);
                view.settingsButtonDisable();
            }
        }
    }

    @Override
    public void onResetButtonClick() {
        player1.resetPoints(preferences);
        player2.resetPoints(preferences);

        settingsVisible = false;
        poisonVisible = false;
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        view.settingsButtonDisable();
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        view.poisonButtonDisable();

        view.setLifepoints(PlayerID.ONE, String.format("%02d",player1.getLifePoints()));
        view.setLifepoints(PlayerID.TWO, String.format("%02d",player2.getLifePoints()));

        view.setPoisonpoints(PlayerID.ONE, String.format("%02d",player1.getPoisonPoints()));
        view.setPoisonpoints(PlayerID.TWO, String.format("%02d",player2.getPoisonPoints()));

        if(view.getPlayerAmount() == 4) {
            player3.resetPoints(preferences);
            player4.resetPoints(preferences);

            view.setLifepoints(PlayerID.THREE, String.format("%02d",player3.getLifePoints()));
            view.setLifepoints(PlayerID.FOUR, String.format("%02d",player4.getLifePoints()));

            view.setPoisonpoints(PlayerID.THREE, String.format("%02d",player3.getPoisonPoints()));
            view.setPoisonpoints(PlayerID.FOUR, String.format("%02d",player4.getPoisonPoints()));
        }
    }

    //region NavDrawer Handling
    @Override
    public void onMenuEntryTogglePlayerTap() {
        if(view.getPlayerAmount() == 4) {
            // Load 2 Player View
            PreferenceManager.saveDefaultPlayerAmount(preferences, 2);
        } else if(view.getPlayerAmount() == 2) {
            // Load 4 Player View
            PreferenceManager.saveDefaultPlayerAmount(preferences, 4);
        }
        view.hideNavigationDrawer();
        view.restartActivity();
    }

    @Override
    public void onMenuEntryDicingTap() {
        view.loadActivity(DicingActivity.class);
    }

    @Override
    public void onMenuEntryEnergySaveTap() {
        togglePowerSavingMode();
    }

    @Override
    public void onMenuEntrySettingsTap() {
        onSettingsButtonClick(ClickType.LONG);
    }

    @Override
    public void onMenuEntryAboutTap() {
        view.loadActivity(AboutActivity.class);
    }

    @Override
    public void onMenuEntryCounterManagerTap() {
        view.loadActivity(CounterActivity.class);
    }
    //endregion
}
