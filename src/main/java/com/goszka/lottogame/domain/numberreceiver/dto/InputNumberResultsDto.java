package com.goszka.lottogame.domain.numberreceiver.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record InputNumberResultsDto(String message, LocalDateTime drawDate, String ticketId, Set<Integer> numbersFromUser) {
}
