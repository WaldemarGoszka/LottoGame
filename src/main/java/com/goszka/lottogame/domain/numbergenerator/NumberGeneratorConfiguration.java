package com.goszka.lottogame.domain.numbergenerator;

import com.goszka.lottogame.domain.numberreceiver.NumberReceiverFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NumberGeneratorConfiguration {

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(RandomNumberGenerable randomNumbersGenerable,
                                                                WinningNumbersRepository winningNumbersRepository,
                                                                NumberReceiverFacade numberReceiverFacade,
                                                                WinningNumbersGeneratorFacadeConfigurationProperties properties){
        WinningNumbersValidator winningNumbersValidator = new WinningNumbersValidator();
        return new WinningNumbersGeneratorFacade(randomNumbersGenerable,winningNumbersValidator,winningNumbersRepository,numberReceiverFacade, properties);
    }

    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable randomNumbersGenerable,
                                                WinningNumbersRepository winningNumbersRepository,
                                                NumberReceiverFacade numberReceiverFacade){
        WinningNumbersGeneratorFacadeConfigurationProperties properties = WinningNumbersGeneratorFacadeConfigurationProperties.builder()
                .count(6)
                .lowerBand(1)
                .upperBand(99)
                .build();
        return winningNumbersGeneratorFacade(randomNumbersGenerable,winningNumbersRepository,numberReceiverFacade, properties);
    }
}
