package com.goszka.lottogame.domain.numbergenerator;

import com.goszka.lottogame.domain.numberreceiver.NumberReceiverFacade;

public class NumberGeneratorConfiguration {

    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable randomNumberGenerable,
                                                WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade){
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumberGenerable,winningNumbersValidator,winningNumbersRepository,numberReceiverFacade);
    }
}
