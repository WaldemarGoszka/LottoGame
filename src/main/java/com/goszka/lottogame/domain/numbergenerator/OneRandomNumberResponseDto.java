package com.goszka.lottogame.domain.numbergenerator;

import lombok.Builder;

@Builder
public record OneRandomNumberResponseDto(
        int number
) {
}