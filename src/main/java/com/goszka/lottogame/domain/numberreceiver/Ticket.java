package com.goszka.lottogame.domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.Set;

record Ticket(String hash, Set<Integer> numbers, LocalDateTime drawDate) {
}
