package com.goszka.lottogame.domain.numbergenerator;

import java.util.Set;

class WinningNumbersGeneratorTestImpl implements RandomNumberGenerable {
    private final Set<Integer> generatedNumbers;

    WinningNumbersGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    WinningNumbersGeneratorTestImpl(Set<Integer> winningNumbers){
        this.generatedNumbers = winningNumbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers(int count, int lowerBand, int upperBand) {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}
