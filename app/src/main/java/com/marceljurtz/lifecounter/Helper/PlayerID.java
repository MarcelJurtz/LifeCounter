package com.marceljurtz.lifecounter.Helper;

public enum PlayerID {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    private final int value;
    private PlayerID(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
