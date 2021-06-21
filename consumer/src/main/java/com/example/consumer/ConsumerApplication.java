package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Bean
    public Consumer<?> personsInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "personsInputChannel", value);
        };
    }

    @Bean
    public Consumer<?> othersInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "othersInputChannel", value);
        };
    }

    @Bean
    public Consumer<?> allInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "allInputChannel", value);
        };
    }
}
