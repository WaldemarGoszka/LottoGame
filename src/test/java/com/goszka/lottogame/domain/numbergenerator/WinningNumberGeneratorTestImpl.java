package com.goszka.lottogame.domain.numbergenerator;

import java.util.Set;

class WinningNumberGeneratorTestImpl implements RandomNumberGenerable {
    private final Set<Integer> generatedNumbers;

    WinningNumberGeneratorTestImpl() {
        this.generatedNumbers = Set.of(1, 2, 3, 4, 5, 6);
    }

    WinningNumberGeneratorTestImpl(Set<Integer> winningNumbers){
        this.generatedNumbers = winningNumbers;
    }

    @Override
    public SixRandomNumbersDto generateSixRandomNumbers() {
        return SixRandomNumbersDto.builder()
                .numbers(generatedNumbers)
                .build();
    }
}
