package com.goszka.lottogame.domain.resultchecker;

import com.goszka.lottogame.domain.numberreceiver.dto.TicketDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WinnerRetriever {
    private final static int NUMBERS_WHEN_PLAYER_WON = 3;

    public List<Player> retrieveWinners(List<Ticket> allTicketsByDate, Set<Integer> winningNumbers) {
        return allTicketsByDate.stream()
                .map(ticket -> {
                    Set<Integer> hitNumbers = calculateHits(winningNumbers, ticket);
                    return buildPlayer(ticket, hitNumbers);
                })
                .toList();
    }


    private Set<Integer> calculateHits(Set<Integer> winningNumbers, Ticket ticket) {
        return ticket.numbers().stream()
                .filter(winningNumbers::contains)
                .collect(Collectors.toSet());
    }

    private Player buildPlayer(Ticket ticket, Set<Integer> hitNumbers) {
        Player.PlayerBuilder builder = Player.builder();
        if (isWinner(hitNumbers)) {
            builder.isWinner(true);
        }
        return builder
                .hash(ticket.hash())
                .numbers(ticket.numbers())
                .hitNumbers(hitNumbers)
                .drawDate(ticket.drawDate())
                .build();
    }

    private boolean isWinner(Set<Integer> hitNumbers) {
        return hitNumbers.size() >= NUMBERS_WHEN_PLAYER_WON;
    }
}
