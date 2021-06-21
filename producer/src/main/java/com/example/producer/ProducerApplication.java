package com.example.producer;

import com.example.producer.dtos.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
@Slf4j
public class ProducerApplication {

    public static final String ROUTING_KEY = "myRoutingKey";

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public Supplier<Message<Person>> first() {
        return () -> getMessage("first.message.key");
    }

    @Bean
    public Supplier<Message<Person>> second() {
        return () -> getMessage("second.message.key");
    }

    @Bean
    public Supplier<Message<Person>> third() {
        return () -> getMessage("third.message.key");
    }

    private Message<Person> getMessage(String routingKey) {
        String id = UUID.randomUUID().toString();
        Person person = new Person(id, routingKey + "-" + id, LocalDateTime.now().getSecond());
        Message<Person> message = MessageBuilder
                .withPayload(person)
                .setHeader(ROUTING_KEY, routingKey)
                .build();
        log.info("Sending person: {}", message.getPayload());
        return message;
    }
}
