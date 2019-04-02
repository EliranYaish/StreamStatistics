package com.java.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = { "com.java" })
public class StatisticsClient 
{
	Logger logger = LoggerFactory.getLogger(StatisticsClient.class);

    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8081");
    }

    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                .uri("/statistics")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .map(s ->s)
                .subscribe();
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(StatisticsClient.class).properties(java.util.Collections.singletonMap("server.port", "8081"))
            .run(args);
    }

}
