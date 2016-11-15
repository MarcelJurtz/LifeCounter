package com.marceljurtz.lifecounter;

import android.widget.TextView;

/**
 * Created by marcel on 11.11.16.
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
    public void initPoints() {
        this.lifePoints = this.defaultLifepoints;
        this.poisonPoints = this.defaultPoisonpoints;
    }

    public void reset(TextView txtLifepoints, TextView txtPoisonpoints) {
        this.poisonPoints = defaultPoisonpoints;
        txtPoisonpoints.setText(this.poisonPoints+"");
        this.lifePoints = defaultLifepoints;
        txtLifepoints.setText(this.lifePoints+"");

    }
    public void updateLifepoints(int lp, TextView txtLifePoints) {
        this.lifePoints += lp;
        if(this.lifePoints > ValueService.getMaxLife()) {
            this.lifePoints = ValueService.getMaxLife();
        } else if(this.lifePoints < ValueService.getMinLife()) {
            this.lifePoints = ValueService.getMinLife();
        }
        txtLifePoints.setText(this.lifePoints+"");
    }

    // Update TextView "PoisonPoints" to given new Value
    // negative int for decreasing poisonpoints
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
