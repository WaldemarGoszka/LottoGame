package com.goszka.lottogame.domain.numbergenerator;

public interface OneRandomNumberFetcher {
    OneRandomNumberResponseDto retrieveOneRandomNumber(int lowerBand, int upperBand);
}