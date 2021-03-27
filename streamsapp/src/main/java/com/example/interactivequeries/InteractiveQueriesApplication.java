package com.example.interactivequeries;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class InteractiveQueriesApplication {

	public final static String PROCESSED_MESSAGES_TOPIC = "processed-messages";
	public static final String PROCESSED_MESSAGES_STORE_NAME = "processed-messages-store";

	public static void main(String[] args) {
		SpringApplication.run(InteractiveQueriesApplication.class, args);
	}

	@Bean
	public Properties kafkaStreamsProperties() throws IOException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(
				String.format("kafkastreams-%s.properties", System.getenv("SPRING_PROFILES_ACTIVE")));
		prop.load(stream);
		return prop;
	}

	@Bean
	public KafkaStreams stream(Properties kafkaStreamsProperties) {
		StreamsBuilder streamsBuilder = new StreamsBuilder();
		streamsBuilder.table(
				PROCESSED_MESSAGES_TOPIC,
				Consumed.with(new Serdes.StringSerde(), new Serdes.StringSerde()),
				Materialized.as(PROCESSED_MESSAGES_STORE_NAME)
		);
		var topology = streamsBuilder.build();
		KafkaStreams kafkaStreams = new KafkaStreams(topology, kafkaStreamsProperties);
		kafkaStreams.start();
		return kafkaStreams;
	}

}

