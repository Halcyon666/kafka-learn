package com.whalefall.kafkalearn.custom;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author Halcyon
 * @since 1.0.0
 */
@Component
@Slf4j
public class GreetingKafkaProducerTask {
    @Resource
    private KafkaTemplate<String, Greeting> greetingKafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void produceMessageToMyTopic() {
        String topic = "greeting";
        Greeting greeting = new Greeting("Hello", "World");
        CompletableFuture<SendResult<String, Greeting>> future = greetingKafkaTemplate.send(topic, greeting);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", greeting, result.getRecordMetadata().offset());
            } else {
                log.info("Unable to send message=[{}] due to : {}", greeting, ex.getMessage());
            }
        });
    }

}
