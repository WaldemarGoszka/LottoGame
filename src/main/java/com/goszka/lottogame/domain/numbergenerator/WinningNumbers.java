package com.goszka.lottogame.domain.numbergenerator;

import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Builder
record WinningNumbers(String id,
                      Set<Integer> winningNumbers,
                      LocalDateTime date) {
}
