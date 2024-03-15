package com.goszka.lottogame.domain.resultchecker;

import com.goszka.lottogame.domain.numberreceiver.dto.TicketDto;
import com.goszka.lottogame.domain.resultchecker.dto.PlayerDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnerRetriever {
    public List<Player> retrieveWinners(Set<Integer> winningNumbers, List<TicketDto> allTicketsByDate) {
        return allTicketsByDate.stream()
                .filter(ticketDto -> ticketDto.numbers().equals(winningNumbers))
                .map(ticketDto -> Player.builder()
                        .hash(ticketDto.hash())
                        .numbers(ticketDto.numbers())
                        .build())
                .collect(Collectors.toList());
    }
}
