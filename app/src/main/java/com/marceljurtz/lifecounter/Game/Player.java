package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

public class Player {
    private int lifePoints;
    private int poisonPoints;
    private PlayerID playerID;

    private final int DEFAULT_LIFEPOINTS = 20;

    public Player(PlayerID id) {
        this.playerID = id;
        this.lifePoints = DEFAULT_LIFEPOINTS;
        this.poisonPoints = 0;
    }

    public void resetPoints(SharedPreferences preferences) {
        this.lifePoints = PreferenceManager.getDefaultLifepoints(preferences);
        this.poisonPoints = 0;
    }

    public PlayerID getPlayerID() {
        return this.playerID;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public int getPoisonPoints() { return this.poisonPoints; }

    public void updateLifepoints(int amount) {
        this.lifePoints += amount;
        if(this.lifePoints < PreferenceManager.getMinLife()) {
            this.lifePoints = PreferenceManager.getMinLife();
        } else if(this.lifePoints > PreferenceManager.getMaxLife()) {
            this.lifePoints = PreferenceManager.getMaxLife();
        }
    }

    public void updatePoisonpoints(int amount) {
        this.poisonPoints += amount;
        if(this.poisonPoints < PreferenceManager.getMinPoison()) {
            this.poisonPoints = PreferenceManager.getMinPoison();
        } else if(this.poisonPoints > PreferenceManager.getMaxPoison()) {
            this.lifePoints = PreferenceManager.getMaxPoison();
        }
    }
}
