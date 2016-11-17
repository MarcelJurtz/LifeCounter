package com.marceljurtz.lifecounter;

import android.widget.TextView;

/**
 * Klasse repräsentiert einen Spieler
 * Funktion: Verwaltung Punkt Leben/Gift
 */
public class Player {
    private int lifePoints;
    private int poisonPoints;
    private int defaultLifepoints;
    private int defaultPoisonpoints;

    public void setDefaults(int defaultLP, int defaultPP) {
        this.defaultPoisonpoints = defaultPP;
        this.defaultLifepoints = defaultLP;
    }

    public Player() {
        this.lifePoints = 0;
        this.poisonPoints = 0;
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


}
