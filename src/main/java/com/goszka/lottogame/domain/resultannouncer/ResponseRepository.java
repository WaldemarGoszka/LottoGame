package com.goszka.lottogame.domain.resultannouncer;

import java.util.Optional;

public interface ResponseRepository {
    ResultResponse save(ResultResponse resultResponse);

    boolean existsById(String hash);

    Optional<ResultResponse> findById(String hash);
}
