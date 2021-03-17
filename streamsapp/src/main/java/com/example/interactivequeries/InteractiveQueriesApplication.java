package com.example.interactivequeries;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class InteractiveQueriesApplication {

	public final static String PROCESSED_MESSAGES_TOPIC = "processed-messages";
	public static final String PROCESSED_MESSAGES_STORE_NAME = "processed-messages-store";

	public static void main(String[] args) {
		SpringApplication.run(InteractiveQueriesApplication.class, args);
	}

	@Bean
	public KTable<String, Integer> kTable(StreamsBuilder kStreamBuilder) {
		return kStreamBuilder.table(
				PROCESSED_MESSAGES_TOPIC,
				Consumed.with(new Serdes.StringSerde(), new Serdes.IntegerSerde()),
				Materialized.as(PROCESSED_MESSAGES_STORE_NAME)
		);
	}

}

