package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.graphics.Color;

import com.marceljurtz.lifecounter.ColorService;
import com.marceljurtz.lifecounter.R;
import com.marceljurtz.lifecounter.Settings.SettingsService;

public class GameController {

    private Context context;
    private GameModel gameModel;
    private GameActivity gameActivity;

    public GameController(GameActivity gameActivity, Context context) {
        this.context = context;
        this.gameActivity = gameActivity;
        gameModel = new GameModel(context);

        // Initiate default colors
        gameActivity.initColorButtonBlack(getBlackInt());
        gameActivity.initColorButtonBlue(getBlueInt());
        gameActivity.initColorButtonGreen(getGreenInt());
        gameActivity.initColorButtonRed(getRedInt());
        gameActivity.initColorButtonWhite(getWhiteInt());
    }

    private Color getBlack() {
        return gameModel.getColor(getString(R.string.shared_preferences_color_black), gameModel.default_black);
    }

    private Color getBlue() {
        return gameModel.getColor(getString(R.string.shared_preferences_color_blue), gameModel.default_blue);
    }

    private Color getGreen() {
        return gameModel.getColor(getString(R.string.shared_preferences_color_green), gameModel.default_green);
    }

    private Color getRed() {
        return gameModel.getColor(getString(R.string.shared_preferences_color_red), gameModel.default_red);
    }

    private Color getWhite() {
        return gameModel.getColor(getString(R.string.shared_preferences_color_white), gameModel.default_white);
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
}
