package com.marceljurtz.lifecounter.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.PlayerID;
import com.marceljurtz.lifecounter.Helper.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int lifePoints;
    private int poisonPoints;
    private PlayerID playerID;
    private String playerIdentification;

    private ArrayList<Counter> counters;

    private final int DEFAULT_LIFEPOINTS = 20;

    public Player(PlayerID id) {
        this.playerID = id;
        this.lifePoints = DEFAULT_LIFEPOINTS;
        this.poisonPoints = 0;
        counters = new ArrayList<Counter>();
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

    public String getPlayerIdentification() {
        return playerIdentification;
    }

    public void setPlayerIdentification(String identification){
        playerIdentification = identification;
    }

    public void AddCounter(Counter c) {
        counters.add(c);
    }

    public void RemoveCounter(Counter c) {
        counters.remove(c);
        c = null;
    }

    public ArrayList<Counter> GetAllCounters() {
        return counters;
    }

    @Override
    public String toString() {
        if(playerIdentification == null ||playerIdentification == "") {
            int id = 0;

            switch (playerID) {
                case ONE:
                    id = 1;
                    break;
                case TWO:
                    id = 2;
                    break;
                case THREE:
                    id = 3;
                    break;
                case FOUR:
                    id = 4;
                    break;
                default:
                    break;
            }
            return "Player " + id;
        }
        else return playerIdentification;
    }
}
