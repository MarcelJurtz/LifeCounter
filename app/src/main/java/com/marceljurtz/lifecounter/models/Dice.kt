package com.marceljurtz.lifecounter.models

import java.util.Random

class Dice {
    private val die: Random

    init {
        this.die = Random()
    }

    fun throwDice(): Int {
        return die.nextInt(20) + 1
    }
}
