package com.goszka.lottogame.domain.resultchecker;

import com.goszka.lottogame.domain.numberreceiver.dto.TicketDto;
import com.goszka.lottogame.domain.resultchecker.dto.PlayersDto;
import com.goszka.lottogame.domain.resultchecker.dto.ResultDto;

import java.util.List;

class ResultCheckerMapper {
    static List<ResultDto> mapPlayersToResults(List<Player> players) {
        return players.stream()
                .map(player -> ResultDto.builder()
                        .hash(player.hash())
                        .numbers(player.numbers())
                        .hitNumbers(player.hitNumbers())
                        .drawDate(player.drawDate())
                        .isWinner(player.isWinner())
                        .build())
                .toList();
    }

    static List<Ticket> mapFromTicketDto(List<TicketDto> allTicketsByDate) {
        return allTicketsByDate.stream()
                .map(ticketDto -> Ticket.builder()
                        .drawDate(ticketDto.drawDate())
                        .hash(ticketDto.hash())
                        .numbers(ticketDto.numbers())
                        .build())
                .toList();
    }
}
