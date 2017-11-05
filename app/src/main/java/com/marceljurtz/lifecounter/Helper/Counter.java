package com.marceljurtz.lifecounter.Helper;

public class Counter {

    private String description;
    private int atk;
    private int def;

    public Counter(String description, int ATK, int DEF) {
        this.description = description;
        this.atk = ATK;
        this.def = DEF;
    }

    public void increaseATK() {
        this.atk++;
    }

    public void decreaseATK() {
        this.atk--;
    }

    public void increaseDEF() {
        this.def++;
    }

    public void decreaseDEF() {
        this.def--;
    }

    public int getATK() {
        return this.atk;
    }

    public int getDEF() {
        return this.def;
    }

    public String getDescription() {
        return this.description;
    }
}
