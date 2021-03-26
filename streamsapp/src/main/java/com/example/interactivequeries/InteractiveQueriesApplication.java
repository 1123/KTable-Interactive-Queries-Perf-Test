package com.example.interactivequeries;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
public class InteractiveQueriesApplication {

	public final static String PROCESSED_MESSAGES_TOPIC = "processed-messages";
	public static final String PROCESSED_MESSAGES_STORE_NAME = "processed-messages-store";

	public static void main(String[] args) {
		SpringApplication.run(InteractiveQueriesApplication.class, args);
	}

	@Bean
	public KafkaStreams stream() {
		Properties properties = new Properties();
		properties.put("application.id", "ktable-test");
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("num.stream.threads", "4");
		properties.put("num.standby.replicas", 1);
		properties.put("state.dir", String.format("/tmp/kafka-streams/%s-%s", System.getenv("APP_HOST"), System.getenv("APP_PORT")));
		// The following property is by default set to 10 minutes.
		// This leads to remote querying of state being impossible in the first 10 minutes after startup.
		// Not sure why the default value is chosen so large.
		properties.put("probing.rebalance.interval.ms", 60000);
		properties.put("application.server", String.format("%s:%s", System.getenv("APP_HOST"), System.getenv("APP_PORT")));
		StreamsBuilder streamsBuilder = new StreamsBuilder();
		var table = streamsBuilder.table(
				PROCESSED_MESSAGES_TOPIC,
				Consumed.with(new Serdes.StringSerde(), new Serdes.StringSerde()),
				Materialized.as(PROCESSED_MESSAGES_STORE_NAME)
		);
		var topology = streamsBuilder.build();
		KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
		kafkaStreams.start();
		return kafkaStreams;
	}

}

