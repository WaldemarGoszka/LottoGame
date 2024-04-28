package com.goszka.lottogame.http.numbergenerator;

import org.springframework.web.client.RestTemplate;
import com.goszka.lottogame.domain.numbergenerator.RandomNumberGenerable;
import com.goszka.lottogame.infrastructure.numbergenerator.http.RandomGeneratorClientConfig;

public class RandomGeneratorRestTemplateTestConfig extends RandomGeneratorClientConfig {

    public RandomNumberGenerable remoteNumberGeneratorClient(int port, int connectionTimeout, int readTimeout) {
        RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return remoteNumberGeneratorClient(restTemplate, "http://localhost", port);
    }
}
