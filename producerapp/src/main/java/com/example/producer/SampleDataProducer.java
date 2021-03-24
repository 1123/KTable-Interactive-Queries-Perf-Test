package com.example.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class SampleDataProducer implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, Integer> kafkaTemplate;

    @Override
    public void run(String... args) throws ExecutionException, InterruptedException {
        for (int i = 1; i <= 1000; i++) {
            ProducerRecord<String, Integer> producerRecord = new ProducerRecord<>(
                    "processed-messages",
                    UUID.randomUUID().toString(),
                    1
            );
            kafkaTemplate.send(producerRecord);
            if (i % 100 == 0) {
                log.info("Produced {} messages.", i);
                log.info("last message key: {}", producerRecord.key());
            }
        }
    }
}
