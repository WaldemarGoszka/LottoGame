package com.goszka.lottogame.domain.numberreceiver;

import com.goszka.lottogame.domain.numberreceiver.dto.InputNumberResultsDto;
import lombok.AllArgsConstructor;

import java.util.Set;
@AllArgsConstructor
public class NumberReceiverFacade {
    public static final String FAILURE_MESSAGE = "failed";
    public static final String SUCCESS_MESSAGE = "success";
    private NumberValidator validator;


    public InputNumberResultsDto input(Set<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange)
            return InputNumberResultsDto.builder().message(SUCCESS_MESSAGE).build();
        return InputNumberResultsDto.builder().message(FAILURE_MESSAGE).build();
    }
}
