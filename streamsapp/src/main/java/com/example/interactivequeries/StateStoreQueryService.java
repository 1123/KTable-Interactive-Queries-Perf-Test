package com.example.interactivequeries;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyQueryMetadata;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.StreamsMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
@Slf4j
public class StateStoreQueryService {

    private static final String STORE_NAME = "processed-messages-store";

    @Autowired
    private KafkaStreams kafkaStreams;

    @GetMapping("/store/{id}")
    public String getSomeKey(@PathVariable String id) {
        ReadOnlyKeyValueStore<String, String> store =
            kafkaStreams.store(STORE_NAME, QueryableStoreTypes.keyValueStore());
        var localResult = store.get(id);
        if (localResult != null) return localResult;
        log.info(kafkaStreams.allMetadata().toString());
        KeyQueryMetadata streamsMetaData = kafkaStreams.queryMetadataForKey(STORE_NAME, id, Serdes.String().serializer());
        RestTemplate restTemplate = new RestTemplate();
        log.info("Querying remote instance: {}", streamsMetaData.toString());
        return restTemplate.getForEntity(
                String.format("http://%s:%s/store/%s",
                        streamsMetaData.activeHost().host(),
                        streamsMetaData.activeHost().port(),
                        id
                ), String.class
        ).getBody();
    }

}
