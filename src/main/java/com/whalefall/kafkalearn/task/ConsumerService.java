package com.whalefall.kafkalearn.task;

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
public class ConsumerService {
    @KafkaListener(topics = "baeldung", groupId = "my-group0")
    public void listen() {
        log.info("received message in simple listener");
    }

/*    @KafkaListener(topics = "baeldung", groupId = "my-group1")
    public void listenWithPayLoad(@Payload String message) {
        log.info("received message, Payload: {}", message);
    }

    @KafkaListener(topics = "baeldung", groupId = "my-group2")
    public void listenWithHeaders(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message: {}, from partition: {}", message, partition);
    }

    @KafkaListener(
            topicPartitions = @TopicPartition(topic = "baeldung",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "100")),
            containerFactory = "partitionsKafkaListenerContainerFactory")
    public void listenToPartition(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received Message with initialOffset 100, message: {}, from partition: {}", message, partition);
    }


    *//**
     * In this listener, all the messages matching the filter will be discarded.
     *//*
    @KafkaListener(
            topics = "baeldung",
            groupId = "my-group3",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenWithFilter(String message) {
        log.info("Received Message in filtered listener: {}", message);
    }*/
}
