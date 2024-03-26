package com.goszka.lottogame.domain.numbergenerator;

import lombok.Builder;

import java.util.Set;

@Builder
public record SixRandomNumbersDto(Set<Integer> numbers) {
}
