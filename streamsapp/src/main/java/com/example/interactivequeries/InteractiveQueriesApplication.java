package com.example.interactivequeries;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class InteractiveQueriesApplication {

	public final static String PROCESSED_MESSAGES_TOPIC = "processed-messages";
	public static final String PROCESSED_MESSAGES_STORE_NAME = "processed-messages-store";

	public static void main(String[] args) {
		SpringApplication.run(InteractiveQueriesApplication.class, args);
	}

	/*
     * We want spring-kafka to NOT clean up the state stores upon startup or shutdown.
     * Therefore we need to register our own instance of the StreamsBuilderFactoryBean.
	 */
	@Bean
	public StreamsBuilderFactoryBean myKafkaStreamsBuilder(KafkaStreamsConfiguration streamsConfig) {
		return new StreamsBuilderFactoryBean(streamsConfig, new CleanupConfig(false, false));
	}

	@Bean
	public KTable<String, Integer> kTable(@Qualifier("myKafkaStreamsBuilder") StreamsBuilder kStreamBuilder) {
		return kStreamBuilder.table(
				PROCESSED_MESSAGES_TOPIC,
				Consumed.with(new Serdes.StringSerde(), new Serdes.IntegerSerde()),
				Materialized.as(PROCESSED_MESSAGES_STORE_NAME)
		);
	}

	/*
	 * Dummy stream to avoid "invalid topology exception". Works around the following bug in spring-kafka:
	 * https://github.com/micronaut-projects/micronaut-kafka/issues/196
	 */
	@Bean
	public KStream<String, Integer> dummyStream(@Qualifier("defaultKafkaStreamsBuilder") StreamsBuilder kStreamBuilder) {
		return kStreamBuilder.stream(
				PROCESSED_MESSAGES_TOPIC,
				Consumed.with(new Serdes.StringSerde(), new Serdes.IntegerSerde())
		);
	}

}

