package com.example.producer;

import com.example.interactivequeries.InteractiveQueriesApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SampleDataProducer implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    @Override
    public void run(String... args) {
        for (int i = 1; i <= 100000; i++) {
            ProducerRecord<String, Integer> producerRecord = new ProducerRecord<>(
                    InteractiveQueriesApplication.PROCESSED_MESSAGES_TOPIC,
                    UUID.randomUUID().toString(),
                    1
            );
            kafkaTemplate.send(producerRecord);
            if (i % 10000 == 0) {
                log.info("Produced {} messages.", i);
                log.info("last message key: {}", producerRecord.key());
            }
        }
    }
}
