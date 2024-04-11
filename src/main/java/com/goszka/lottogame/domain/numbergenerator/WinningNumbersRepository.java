package com.goszka.lottogame.domain.numbergenerator;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WinningNumbersRepository extends MongoRepository<WinningNumbers, String> {
    WinningNumbers save(WinningNumbers winningNumbers);
    Optional<WinningNumbers> findNumbersByDate(LocalDateTime date);
    boolean existsByDate(LocalDateTime date);
}
