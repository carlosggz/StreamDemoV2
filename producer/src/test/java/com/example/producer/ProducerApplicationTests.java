package com.example.producer;

import com.example.producer.models.Person;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableTestBinder
class ProducerApplicationTests {

    static final String TOPIC_NAME = "persons-topic";
    static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OutputDestination outputDestination;

    @Test
    void contextLoads() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"first.message.key", "second.message.key", "third.message.key"})
    void producersSendMessages(String routingKey) {
        outputDestination.clear(TOPIC_NAME);

        await()
                .atMost(5, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    val message = outputDestination.receive(1000, TOPIC_NAME);
                    assertNotNull(message);
                    assertEquals(routingKey, message.getHeaders().get(ProducerApplication.ROUTING_KEY));
                    val person = objectMapper.readValue(message.getPayload(), Person.class);
                    assertNotNull(person);
                    System.out.println(person);
                });
    }
}
