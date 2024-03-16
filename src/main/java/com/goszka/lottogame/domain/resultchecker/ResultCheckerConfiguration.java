package com.goszka.lottogame.domain.resultchecker;

import com.goszka.lottogame.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.goszka.lottogame.domain.numberreceiver.NumberReceiverFacade;

public class ResultCheckerConfiguration {
    ResultCheckerFacade createForTest(WinningNumbersGeneratorFacade generatorFacade, NumberReceiverFacade receiverFacade, PlayerRepository playerRepository) {
        WinnerRetriever winnerRetriever = new WinnerRetriever();
        return new ResultCheckerFacade(generatorFacade, receiverFacade, playerRepository, winnerRetriever);
    }
}
