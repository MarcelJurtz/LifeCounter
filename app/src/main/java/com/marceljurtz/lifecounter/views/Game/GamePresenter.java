package com.marceljurtz.lifecounter.views.Game;

import android.content.SharedPreferences;

import com.marceljurtz.lifecounter.views.Base.Presenter;
import com.marceljurtz.lifecounter.enums.ClickTypeEnum;
import com.marceljurtz.lifecounter.models.Color;
import com.marceljurtz.lifecounter.models.Game;
import com.marceljurtz.lifecounter.enums.MagicColorEnum;
import com.marceljurtz.lifecounter.enums.OperatorEnum;
import com.marceljurtz.lifecounter.models.Player;
import com.marceljurtz.lifecounter.enums.PlayerIdEnum;
import com.marceljurtz.lifecounter.models.PreferenceManager;
import com.marceljurtz.lifecounter.views.Settings.SettingsActivity;

public class GamePresenter extends Presenter implements IGamePresenter {

    private Game game;
    private IGameView view;

    private boolean settingsVisible;
    private boolean poisonVisible;

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private final int SCREEN_SMALL = 1;
    private final int SCREEN_NORMAL = 2;
    private final int SCREEN_LARGE = 3;
    private final int SCREEN_XLARGE = 4;

    private boolean hideOtherControlsWhenSettingsDisplayed = false;

    GamePresenter(IGameView view, SharedPreferences preferences) {
        super(view, preferences);

        this.view = view;

        int screenLayout = view.getScreenSize();
        // Configuration.SCREENLAYOUT_SIZE_LARGE;   --> 3
        // Configuration.SCREENLAYOUT_SIZE_NORMAL;  --> 2
        // Configuration.SCREENLAYOUT_SIZE_SMALL;   --> 1
        // Configuration.SCREENLAYOUT_SIZE_XLARGE;  --> 4

        if(screenLayout != SCREEN_XLARGE && view.getPlayerAmount() == 4) hideOtherControlsWhenSettingsDisplayed = true;

        player1 = new Player(PlayerIdEnum.ONE); // SCHEISSE
        player2 = new Player(PlayerIdEnum.TWO);

        if(view.getPlayerAmount() == 4) {
            player3 = new Player(PlayerIdEnum.THREE);
            player4 = new Player(PlayerIdEnum.FOUR);
            game = new Game(_preferences, new Player[]{player1, player2, player3, player4});
        } else {
            game = new Game(_preferences, new Player[]{player1, player2});
        }


        // Initiate default colors
        view.initColorButton(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences));
        view.initColorButton(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences));
        view.initColorButton(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences));
        view.initColorButton(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences));
        view.initColorButton(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences));

        // Settings, Energy-Saving & Poison
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, false);
        view.settingsButtonDisable();
        settingsVisible = false;

        view.poisonButtonDisable();
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        poisonVisible = false;
    }

    @Override
    public void onPause() {
        game.saveGameState(_preferences);
    }

    @Override
    public void onResume() {

        if(view.getPlayerAmount() == 4) {
            game.loadGameState(_preferences, 4);
        } else {
            game.loadGameState(_preferences, 2);
        }

        for(Player player : game.getPlayers()) {
            player.setColor(new Color(player.getColorOrDefault().getBasecolor(), PreferenceManager.getCustomizedColorOrDefault(player.getColorOrDefault().getBasecolor(), _preferences)));
            view.setLifepoints(player.getPlayerIdEnum(), String.format("%02d",player.getLifePoints()));
            view.setPoisonpoints(player.getPlayerIdEnum(), String.format("%02d", player.getPoisonPoints()));
            view.setLayoutColor(player.getPlayerIdEnum(), player.getColorOrDefault().getIntValue());
        }

        settingsVisible = false;
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        view.settingsButtonDisable();

        poisonVisible = false;
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        view.poisonButtonDisable();

        _powerSaveEnabled = false;

        view.initColorButton(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences));
        view.initColorButton(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences));
        view.initColorButton(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences));
        view.initColorButton(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences));
        view.initColorButton(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences));

        if(PreferenceManager.getScreenTimeoutDisabled(_preferences)) {
            view.disableScreenTimeout();
        } else {
            view.enableScreenTimeout();
        }

        view.hideNavigationDrawer();
    }

    @Override
    public void onLifeUpdate(PlayerIdEnum playerIdEnum, ClickTypeEnum clickTypeEnum, OperatorEnum operatorEnum) {
        game.updateLifepoints(playerIdEnum, clickTypeEnum, operatorEnum);
        int points = game.getPlayerLifepoints(playerIdEnum);

        String pointsStr = String.format("%02d",points);
        view.setLifepoints(playerIdEnum, pointsStr);
    }

    @Override
    public void onPoisonUpdate(PlayerIdEnum playerIdEnum, ClickTypeEnum clickTypeEnum, OperatorEnum operatorEnum) {
        game.updatePoisonpoints(playerIdEnum, clickTypeEnum, operatorEnum);
        int points = game.getPlayerPoisonpoints(playerIdEnum);

        String pointsStr = String.format("%02d",points);
        view.setPoisonpoints(playerIdEnum, pointsStr);
    }

    @Override
    public void onColorButtonClick(PlayerIdEnum playerIdEnum, MagicColorEnum color, ClickTypeEnum clickTypeEnum) {
        if(clickTypeEnum.equals(ClickTypeEnum.SHORT)) {

            // Disable PowerSaveMode if enabled
            if(_powerSaveEnabled) onMenuEntryEnergySaveTap();

            Color newColor;

            switch (color) {
                case BLUE:
                    newColor = new Color(MagicColorEnum.BLUE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLUE, _preferences));
                    break;
                case GREEN:
                    newColor = new Color(MagicColorEnum.GREEN, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.GREEN, _preferences));
                    break;
                case RED:
                    newColor = new Color(MagicColorEnum.RED, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.RED, _preferences));
                    break;
                case WHITE:
                    newColor = new Color(MagicColorEnum.WHITE, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.WHITE, _preferences));
                    break;
                default:
                    newColor = new Color(MagicColorEnum.BLACK, PreferenceManager.getCustomizedColorOrDefault(MagicColorEnum.BLACK, _preferences));
                    break;
            }

            switch(playerIdEnum) {
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

            view.setLayoutColor(playerIdEnum, newColor.getIntValue());
        } else if(clickTypeEnum.equals(ClickTypeEnum.LONG) && color.equals(MagicColorEnum.BLACK)) {
            onMenuEntryEnergySaveTap();
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
    public void onSettingsButtonClick(ClickTypeEnum clickTypeEnum) {
        if(clickTypeEnum.equals(ClickTypeEnum.LONG)) {
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
        player1.resetPoints(_preferences);
        player2.resetPoints(_preferences);

        settingsVisible = false;
        poisonVisible = false;
        view.disablePoisonControls(hideOtherControlsWhenSettingsDisplayed);
        view.settingsButtonDisable();
        view.disableSettingsControls(hideOtherControlsWhenSettingsDisplayed, poisonVisible);
        view.poisonButtonDisable();

        view.setLifepoints(PlayerIdEnum.ONE, String.format("%02d",player1.getLifePoints()));
        view.setLifepoints(PlayerIdEnum.TWO, String.format("%02d",player2.getLifePoints()));

        view.setPoisonpoints(PlayerIdEnum.ONE, String.format("%02d",player1.getPoisonPoints()));
        view.setPoisonpoints(PlayerIdEnum.TWO, String.format("%02d",player2.getPoisonPoints()));

        if(view.getPlayerAmount() == 4) {
            player3.resetPoints(_preferences);
            player4.resetPoints(_preferences);

            view.setLifepoints(PlayerIdEnum.THREE, String.format("%02d",player3.getLifePoints()));
            view.setLifepoints(PlayerIdEnum.FOUR, String.format("%02d",player4.getLifePoints()));

            view.setPoisonpoints(PlayerIdEnum.THREE, String.format("%02d",player3.getPoisonPoints()));
            view.setPoisonpoints(PlayerIdEnum.FOUR, String.format("%02d",player4.getPoisonPoints()));
        }
    }
}
