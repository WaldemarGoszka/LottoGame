package com.goszka.lottogame;

import com.goszka.lottogame.domain.numbergenerator.WinningNumbersGeneratorFacadeConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({WinningNumbersGeneratorFacadeConfigurationProperties.class})

public class LottoGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(LottoGameApplication.class, args);
    }

}
