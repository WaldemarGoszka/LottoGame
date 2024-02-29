package com.goszka.lottogame.domain.numberreceiver;


import com.goszka.lottogame.domain.numberreceiver.dto.TicketDto;

public class TicketMapper {
    public static TicketDto mapFromTicket(Ticket ticket){
        return TicketDto.builder()
                .ticketId(ticket.ticketId())
                .numbersFromUser(ticket.numbersFromUser())
                .drawDate(ticket.drawDate())
                .build();
    }
}
