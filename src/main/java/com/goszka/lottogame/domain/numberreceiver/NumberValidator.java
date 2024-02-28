package com.goszka.lottogame.domain.numberreceiver;

import java.util.Set;

class NumberValidator {
    public final int MAXIMUM_NUMBERS_FROM_USER = 6;
    boolean areAllNumbersInRange(Set<Integer> numbersFromUser) {
        return numbersFromUser.stream().filter(number -> number <= 99).filter(number -> number >= 1).count() == MAXIMUM_NUMBERS_FROM_USER;
    }
}