package com.goszka.lottogame.domain.resultchecker;

import com.goszka.lottogame.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.goszka.lottogame.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultCheckerConfiguration {
    @Bean
    ResultCheckerFacade resultCheckerFacade(WinningNumbersGeneratorFacade generatorFacade, NumberReceiverFacade receiverFacade, PlayerRepository playerRepository) {
        WinnerRetriever winnerRetriever = new WinnerRetriever();
        return new ResultCheckerFacade(generatorFacade, receiverFacade, playerRepository, winnerRetriever);
    }
}
