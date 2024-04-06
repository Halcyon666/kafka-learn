package com.whalefall.kafkalearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaLearnApplication.class, args);
	}

}
