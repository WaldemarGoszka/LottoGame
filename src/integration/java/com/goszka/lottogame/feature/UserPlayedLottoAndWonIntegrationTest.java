package com.goszka.lottogame;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.goszka.lottogame.feature.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.goszka.lottogame.BaseIntegrationTest;
import com.goszka.lottogame.domain.numbergenerator.WinningNumbersGeneratorFacade;
import com.goszka.lottogame.domain.numbergenerator.WinningNumbersNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import com.goszka.lottogame.domain.numberreceiver.dto.NumberReceiverResponseDto;
import org.springframework.test.web.servlet.MvcResult;
import com.goszka.lottogame.domain.resultannouncer.dto.ResultAnnouncerResponseDto;
import com.goszka.lottogame.domain.resultchecker.PlayerResultNotFoundException;
import com.goszka.lottogame.domain.resultchecker.ResultCheckerFacade;
import com.goszka.lottogame.domain.resultchecker.dto.ResultDto;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlayedLottoAndWonIntegrationTest extends BaseIntegrationTest {

    @Autowired
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    @Autowired
    ResultCheckerFacade resultCheckerFacade;


    @Test
    public void should_user_win_and_system_should_generate_winners() throws Exception {
        //step 1: external service returns 6 random numbers (1,2,3,4,5,6)
        //given && when && then
        wireMockServer.stubFor(WireMock.get("/api/v1.0/random?min=1&max=99&count=25")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [1, 2, 3, 4, 5, 6, 82, 82, 83, 83, 86, 57, 10, 81, 53, 93, 50, 54, 31, 88, 15, 43, 79, 32, 43]
                                          """.trim()
                        )));
        winningNumbersGeneratorFacade.generateWinningNumbers();


        //step 2: system fetched winning numbers for draw date: 19.11.2022 12:00
        //given
        LocalDateTime drawDate = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
        //when && then
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    try {
                        return !winningNumbersGeneratorFacade.retrieveWinningNumbersByDate(drawDate).getWinningNumbers().isEmpty();
                    } catch (WinningNumbersNotFoundException e) {
                        return false;
                    }
                });


        //step 3: user made POST /inputNumbers with 6 numbers (1, 2, 3, 4, 5, 6) at 16-11-2022 10:00 and system returned OK(200) with message: “success” and Ticket (DrawDate:19.11.2022 12:00 (Saturday), TicketId: sampleTicketId)
        //given && when
        ResultActions performPostInputNumbers = mockMvc.perform(post("/inputNumbers")
                .content("""
                        {
                        "inputNumbers": [1,2,3,4,5,6]
                        }
                        """.trim()
                ).contentType(MediaType.APPLICATION_JSON)
        );
        //then
        MvcResult mvcResultPost = performPostInputNumbers.andExpect(status().isOk()).andReturn();
        String jsonPost = mvcResultPost.getResponse().getContentAsString();
        NumberReceiverResponseDto numberReceiverResponseDto = objectMapper.readValue(jsonPost, NumberReceiverResponseDto.class);

        String ticketId = numberReceiverResponseDto.ticketDto().hash();
        assertAll(
                () -> assertThat(numberReceiverResponseDto.ticketDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(ticketId).isNotNull(),
                () -> assertThat(numberReceiverResponseDto.message()).isEqualTo("SUCCESS")
        );


        //step 4: user made GET /results/notExistingId and system returned 404(NOT_FOUND) and body with (message: Not found for id: notExistingId and status NOT_FOUND)
        //given && when
        ResultActions performGetResultWithNotExistingId = mockMvc.perform(get("/results/notExistingId"));
        //then
        performGetResultWithNotExistingId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message": "Not found for id: notExistingId",
                        "status": "NOT_FOUND"
                        }
                        """.trim()
                ));


        //step 5: 3 days and 55 minutes passed, and it is 5 minute before draw date (19.11.2022 11:55)
        //given && when && then
        clock.plusDaysAndMinutes(3,55);


        //step 6: system generated result for TicketId: sampleTicketId with draw date 19.11.2022 12:00, and saved it with 6 hits
        //when && then
        await()
                .atMost(Duration.ofSeconds(20))
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    try{
                        ResultDto result = resultCheckerFacade.findByHash(ticketId);
                        return !result.numbers().isEmpty();
                    }catch (PlayerResultNotFoundException e) {
                        return false;
                    }
                });


        //step 7: 6 minutes passed, and it is 1 minute after the draw (19.11.2022 12:01)
        //given && when && then
        clock.plusMinutes(6);


        // step 8: user made GET /results/sampleTicketId and system returned 200 (OK)
        //given && when
        ResultActions performGetResultsById = mockMvc.perform(get("/results/" + ticketId));
        //then
        MvcResult mvcResultGet = performGetResultsById.andExpect(status().isOk()).andReturn();
        String jsonGet = mvcResultGet.getResponse().getContentAsString();
        ResultAnnouncerResponseDto resultAnnouncerResponseDto = objectMapper.readValue(jsonGet, ResultAnnouncerResponseDto.class);

        assertAll(
                () -> assertThat(resultAnnouncerResponseDto.responseDto().hash()).isNotNull(),
                () -> assertThat(resultAnnouncerResponseDto.responseDto().isWinner()).isTrue(),
                () -> assertThat(resultAnnouncerResponseDto.responseDto().numbers()).isEqualTo(Set.of(1,2,3,4,5,6)),
                () -> assertThat(resultAnnouncerResponseDto.responseDto().hitNumbers()).isEqualTo(Set.of(1,2,3,4,5,6)),
                () -> assertThat(resultAnnouncerResponseDto.responseDto().drawDate()).isEqualTo(drawDate),
                () -> assertThat(resultAnnouncerResponseDto.message()).isEqualTo("Congratulations, you won!")
        );
    }
}