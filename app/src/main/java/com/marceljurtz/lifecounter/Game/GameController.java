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
