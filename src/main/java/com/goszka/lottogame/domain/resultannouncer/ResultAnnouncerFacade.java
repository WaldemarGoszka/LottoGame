package com.goszka.lottogame.domain.resultannouncer;

import lombok.AllArgsConstructor;
import com.goszka.lottogame.domain.resultannouncer.dto.ResponseDto;
import com.goszka.lottogame.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.goszka.lottogame.domain.resultchecker.ResultCheckerFacade;
import com.goszka.lottogame.domain.resultchecker.dto.ResultDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static com.goszka.lottogame.domain.resultannouncer.MessageResponse.*;

@AllArgsConstructor
public class ResultAnnouncerFacade {
    private final ResultCheckerFacade resultCheckerFacade;
    private final ResponseRepository responseRepository;
    private final Clock clock;

    public ResultAnnouncerResponseDto checkResult(String hash) {
        if (responseRepository.existsById(hash)) {
            Optional<ResultResponse> resultResponseCached = responseRepository.findById(hash);
            if (resultResponseCached.isPresent()) {
                return new ResultAnnouncerResponseDto(ResultMapper.mapToDto(resultResponseCached.get()), ALREADY_CHECKED.info);
            }
        }
        ResultDto resultDto = resultCheckerFacade.findByHash(hash);
        if (resultDto == null) {
            return new ResultAnnouncerResponseDto(null, HASH_DOES_NOT_EXIST_MESSAGE.info);
        }
        ResponseDto responseDto = buildResponseDto(resultDto);
        responseRepository.save(buildResponse(responseDto));
        if (responseRepository.existsById(hash) && !isAfterAnnouncementTime(resultDto)) {
            return new ResultAnnouncerResponseDto(responseDto, WAIT_MESSAGE.info);
        }
        if (resultCheckerFacade.findByHash(hash).isWinner()) {
            return new ResultAnnouncerResponseDto(responseDto, WIN_MESSAGE.info);
        }
        return new ResultAnnouncerResponseDto(responseDto, LOSE_MESSAGE.info);
    }

    private ResultResponse buildResponse(ResponseDto responseDto) {
        return ResultResponse.builder()
                .hash(responseDto.hash())
                .numbers(responseDto.numbers())
                .hitNumbers(responseDto.hitNumbers())
                .drawDate(responseDto.drawDate())
                .isWinner(responseDto.isWinner())
                .build();
    }

    private ResponseDto buildResponseDto(ResultDto resultDto) {
        return ResponseDto.builder()
                .hash(resultDto.hash())
                .numbers(resultDto.numbers())
                .hitNumbers(resultDto.hitNumbers())
                .drawDate(resultDto.drawDate())
                .isWinner(resultDto.isWinner())
                .build();
    }

    private boolean isAfterAnnouncementTime(ResultDto resultDto) {
        LocalDateTime announcementDateTime = resultDto.drawDate();
        LocalDateTime now = LocalDateTime.now(clock);
        return now.isAfter(announcementDateTime);

    }
}
