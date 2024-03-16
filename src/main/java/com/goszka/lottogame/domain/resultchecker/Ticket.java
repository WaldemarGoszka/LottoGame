package com.goszka.lottogame.domain.resultchecker;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
record Ticket(
        String hash,
        Set<Integer> numbers,
        LocalDateTime drawDate) {
}
