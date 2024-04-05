package com.goszka.lottogame.domain.numbergenerator;

import com.goszka.lottogame.domain.numbergenerator.dto.WinningNumbersDto;
import com.goszka.lottogame.domain.numberreceiver.NumberReceiverFacade;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
public class WinningNumbersGeneratorFacade {
    private final RandomNumberGenerable randomGenerable;
    private final WinningNumbersValidator winningNumbersValidator;
    private final WinningNumbersRepository winningNumbersRepository;
    private final NumberReceiverFacade numberReceiverFacade;
    private final WinningNumbersGeneratorFacadeConfigurationProperties properties;

    public WinningNumbersDto generateWinningNumbers() {
        LocalDateTime nextDrawDate = numberReceiverFacade.retrieveNextDrawDate();
        SixRandomNumbersDto sixRandomNumbersDto = randomGenerable.generateSixRandomNumbers(properties.count(), properties.lowerBand(), properties.upperBand());
        Set<Integer> winningNumbers = sixRandomNumbersDto.numbers();
        winningNumbersValidator.validate(winningNumbers);
        winningNumbersRepository.save(WinningNumbers.builder()
                .winningNumbers(winningNumbers)
                .date(nextDrawDate)
                .build());
        return WinningNumbersDto.builder()
                .winningNumbers(winningNumbers)
                .build();
    }

    public WinningNumbersDto retrieveWinningNumbersByDate(LocalDateTime date) {
        WinningNumbers numbersByDate = winningNumbersRepository.findNumbersByDate(date).
                orElseThrow(() -> new WinningNumbersNotFoundException("Not Found"));
        return WinningNumbersDto.builder()
                .winningNumbers(numbersByDate.winningNumbers())
                .date(numbersByDate.date())
                .build();
    }

    public boolean areWinningNumbersGeneratedByDate(){
        LocalDateTime nextDrawDate = numberReceiverFacade.retrieveNextDrawDate();
        return winningNumbersRepository.existsByDate(nextDrawDate);
    }
}
