package com.marceljurtz.lifecounter.Dicing;

import java.util.Random;

public class DicingModel {
    private Random die;

    public DicingModel() {
        this.die = new Random();
    }

    public int ThrowDice() {
        return die.nextInt(20)+1;
    }
}
