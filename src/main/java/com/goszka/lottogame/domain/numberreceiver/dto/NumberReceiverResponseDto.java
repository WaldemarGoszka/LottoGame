package com.goszka.lottogame.domain.numberreceiver.dto;

import lombok.Builder;

@Builder
public record NumberReceiverResponseDto(TicketDto ticketDto, String message) {
}
