package com.goszka.lottogame.domain.numberreceiver;

enum ValidationResult {
    NOT_SIX_NUMBER_GIVEN("YOU SHOULD GIVE 6 UNIQUE NUMBERS"),
    NOT_IN_RANGE("YOU SHOULD GIVE NUMBERS FROM 1 TO 99"),
    INPUT_SUCCESS("SUCCESS");

    final String info;

    ValidationResult(String info) {
        this.info = info;
    }
}
