package com.goszka.lottogame.domain.numberreceiver;


import com.goszka.lottogame.domain.numberreceiver.dto.InputNumberResultsDto;
import com.goszka.lottogame.domain.numberreceiver.dto.TicketDto;
import lombok.AllArgsConstructor;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class NumberReceiverFacade {
    private NumberValidator validator;
    private NumberReceiverRepository repository;
    private final Clock clock;

    public InputNumberResultsDto input(Set<Integer> numbersFromUser) {
        boolean areAllNumbersInRange = validator.areAllNumbersInRange(numbersFromUser);
        if (areAllNumbersInRange) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now(clock);
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbersFromUser));
            return InputNumberResultsDto.builder()
                    .message("success")
                    .drawDate(savedTicket.drawDate())
                    .ticketId(savedTicket.ticketId())
                    .numbersFromUser(savedTicket.numbersFromUser())
                    .build();
        }
        return InputNumberResultsDto.builder()
                .message("failed")
                .build();
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
}
