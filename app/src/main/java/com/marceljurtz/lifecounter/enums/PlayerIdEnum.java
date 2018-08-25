package com.marceljurtz.lifecounter.enums;

public enum PlayerIdEnum {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    private final int value;
    private PlayerIdEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
