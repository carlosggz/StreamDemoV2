package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    public final StatsService service;

    public ConsumerApplication(StatsService service){
        this.service = service;
    }

    @Bean
    public Consumer<?> personsInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "personsInputChannel", value);
            service.incFirst();
        };
    }

    @Bean
    public Consumer<?> othersInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "othersInputChannel", value);
            service.incSecond();
        };
    }

    @Bean
    public Consumer<?> allInputChannel() {
        return value -> {
            log.info("Received person on channel {}: {}", "allInputChannel", value);
            service.incThird();
        };
    }
}
