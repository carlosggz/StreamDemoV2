package com.example.consumer;

import com.example.consumer.models.Person;
import com.example.consumer.services.StatsService;
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
    public Consumer<Person> personsInputChannel() {
        return value -> {
            logInfo("personsInputChannel", value);
            service.incFirst();
        };
    }

    @Bean
    public Consumer<Person> othersInputChannel() {
        return value -> {
            logInfo("othersInputChannel", value);
            service.incSecond();
        };
    }

    @Bean
    public Consumer<Person> allInputChannel() {
        return value -> {
            logInfo("allInputChannel", value);
            service.incThird();
        };
    }

    private void logInfo(String channel, Person value) {
        log.info("Received person on channel {}: {}", channel, value);
    }
}
