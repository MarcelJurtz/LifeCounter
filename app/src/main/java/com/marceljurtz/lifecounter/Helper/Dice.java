package com.marceljurtz.lifecounter.Helper;

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
