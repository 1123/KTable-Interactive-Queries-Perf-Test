package com.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class SampleDataProducer implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 10000000; i++) {
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    "processed-messages",
                    UUID.randomUUID().toString(), "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
            kafkaTemplate.send(producerRecord);
            if (i % 1000000 == 0) {
                log.info("Produced {} messages.", i);
                log.info("last message key: {}", producerRecord.key());
            }
        }
    }
}
