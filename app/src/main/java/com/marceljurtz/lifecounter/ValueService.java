package com.marceljurtz.lifecounter;

/**
 * Verwaltung der Standardwerte
 * Hinweis: Standardanzahl Leben über Shared Preferences in GameActivity
 */
public class ValueService {
    private static final int MAX_POISON = 25;
    private static final int MIN_POISON = 0;
    private static final int MAX_LIFE = 1000;
    private static final int MIN_LIFE = -100;

    public static int getMaxPoison(){
        return MAX_POISON;
    }

    public static int getMinPoison(){
        return MIN_POISON;
    }

    public static int getMaxLife(){
        return MAX_LIFE;
    }

    public static int getMinLife() {
        return MIN_LIFE;
    }
}
