package com.whalefall.kafkalearn.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class KafkaProducerTask {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedRate = 5000)
    public void produceMessageToMyTopic() {
        String topic = "baeldung";
        String message = "hello kafka!";
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            } else {
                log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }

}
