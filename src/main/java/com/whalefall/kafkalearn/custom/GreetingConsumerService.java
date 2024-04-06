package com.whalefall.kafkalearn.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author Halcyon
 * @since 1.0.0
 */
@Service
@Slf4j
public class GreetingConsumerService {
    @KafkaListener(
            topics = "greeting",
            containerFactory = "greetingKafkaListenerContainerFactory")
    public void greetingListener(@Payload Greeting message) {
        log.info("Received Greeting message: {}", message);
    }
}
