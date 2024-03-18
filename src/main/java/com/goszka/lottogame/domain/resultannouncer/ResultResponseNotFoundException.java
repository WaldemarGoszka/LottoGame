package com.goszka.lottogame.domain.resultannouncer;

public class ResultResponseNotFoundException extends RuntimeException {
    ResultResponseNotFoundException(String message) {
        super(message);
    }
}
