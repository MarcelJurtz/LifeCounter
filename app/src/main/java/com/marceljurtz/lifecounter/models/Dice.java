package com.marceljurtz.lifecounter.models;

import java.util.Random;

public class Dice {
    private Random die;

    public Dice() {
        this.die = new Random();
    }

    public int throwDice() {
        return die.nextInt(20)+1;
    }
}
