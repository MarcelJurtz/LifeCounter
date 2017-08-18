package com.marceljurtz.lifecounter.Game;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marceljurtz.lifecounter.Helper.PlayerID;

public class Player {
    private int lifePoints;
    private int poisonPoints;
    private int defaultLifepoints;
    private int defaultPoisonpoints;
    //private TextView txtLifepoints;
    //private TextView txtPoisonpoints;
    private RelativeLayout backgroundLayout;

    private PlayerID playerID;

    private final int DEFAULT_LIFEPOINTS = 20;

    public void setDefaults(int defaultLP, int defaultPP) {
        this.defaultPoisonpoints = defaultPP;
        this.defaultLifepoints = defaultLP;
        this.lifePoints = this.defaultLifepoints;
        this.poisonPoints = this.defaultPoisonpoints;
        //txtLifepoints.setText(String.valueOf(this.lifePoints));
        //txtPoisonpoints.setText(String.valueOf(this.poisonPoints));
    }

    public Player(PlayerID id) {
        this.lifePoints = 0;
        this.poisonPoints = 0;
        this.playerID = id;
        //txtLifepoints = lp;
        //txtPoisonpoints = pp;
    }

    // Neuladen von Standardwerten
    // Bei Änderung der Standardanzahl (Lang-Klick auf Textfeld)
    public void initPoints() {
        this.lifePoints = this.defaultLifepoints;
        this.poisonPoints = this.defaultPoisonpoints;
    }

    // Rücksetzen der Textfelder auf Standardwerte
    public void reset(TextView txtLifepoints, TextView txtPoisonpoints) {
        this.poisonPoints = defaultPoisonpoints;
        txtPoisonpoints.setText(this.poisonPoints+"");
        this.lifePoints = defaultLifepoints;
        txtLifepoints.setText(this.lifePoints+"");

    }

    public PlayerID getPlayerID() {
        return this.playerID;
    }

    public int getLifePoints() {
        return this.lifePoints;
    }

    public int getPoisonPoints() {
        return this.poisonPoints;
    }

    // Update TextView "LifePoints"
    // Eingabe des Werts zur Änderung der aktuellen Zahl (+/-)
    public void updateLifepoints(int lp, TextView txtLifePoints) {
        this.lifePoints += lp;
        if(this.lifePoints > ValueService.getMaxLife()) {
            this.lifePoints = ValueService.getMaxLife();
        } else if(this.lifePoints < ValueService.getMinLife()) {
            this.lifePoints = ValueService.getMinLife();
        }
        txtLifePoints.setText(this.lifePoints+"");
    }

    public void updateLifepoints(int amount) {
        this.lifePoints += amount;
        if(this.lifePoints < 0) {
            this.lifePoints = 0;
        }
    }

    public void updatePoisonpoints(int amount) {
        this.poisonPoints += amount;
        if(this.lifePoints < 0) {
            this.poisonPoints = 0;
        }
    }

    // Update TextView "PoisonPoints"
    // Eingabe des Werts zur Änderung der aktuellen Zahl (+/-)
    public void updatePoisonPoints(int pp, TextView txtPoisonPoints) {
        this.poisonPoints += pp;
        if(this.poisonPoints > ValueService.getMaxPoison()) {
            this.poisonPoints = ValueService.getMaxPoison();
        } else if (poisonPoints < ValueService.getMinPoison()) {
            this.poisonPoints = ValueService.getMinPoison();
        }
        txtPoisonPoints.setText(poisonPoints+"");
    }

    public static int getDefaultLifepoints(Context context) {

    }


}
