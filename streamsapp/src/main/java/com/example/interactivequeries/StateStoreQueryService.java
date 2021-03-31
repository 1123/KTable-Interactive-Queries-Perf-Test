package com.example.interactivequeries;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyQueryMetadata;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class StateStoreQueryService {

    private static final String STORE_NAME = "processed-messages-store";

    @Autowired
    private KafkaStreams kafkaStreams;

    @GetMapping("/threadmetadata")
    public String threadMetadata() throws JsonProcessingException {
        var localThreadsMetadata = kafkaStreams.localThreadsMetadata();
        return new ObjectMapper().writeValueAsString(new AllThreadsMetaDataPojo(localThreadsMetadata));
    }

    @GetMapping("/metadata")
    public String metaData() throws JsonProcessingException {
        var streamsMetaData = kafkaStreams.allMetadata();
        return new ObjectMapper().writeValueAsString(new StreamsMetaDataListPojo(streamsMetaData));
    }

    @GetMapping("/store/{id}")
    public String getKey(@PathVariable String id) {
        log.info("Searching for value in local store");
        ReadOnlyKeyValueStore<String, String> store =
            kafkaStreams.store(STORE_NAME, QueryableStoreTypes.keyValueStore());
        log.info("Approximate number of entries: {}", store.approximateNumEntries());
        var localResult = store.get(id);
        if (localResult != null) {
            log.info("Found result in local store: {}", localResult);
            return localResult;
        }
        log.info(kafkaStreams.allMetadata().toString());
        KeyQueryMetadata streamsMetaData = kafkaStreams.queryMetadataForKey(STORE_NAME, id, Serdes.String().serializer());
        RestTemplate restTemplate = new RestTemplate();
        log.info("Querying remote instance: {}", streamsMetaData.toString());
        return restTemplate.getForEntity(
                String.format("http://%s:%s/remote/%s",
                        streamsMetaData.activeHost().host(),
                        streamsMetaData.activeHost().port(),
                        id
                ), String.class
        ).getBody();
    }

    @GetMapping("/remote/{id}")
    public String getRemoteKey(@PathVariable String id) {
        log.info("Searching for value in local store");
        ReadOnlyKeyValueStore<String, String> store =
                kafkaStreams.store(STORE_NAME, QueryableStoreTypes.keyValueStore());
        log.info("Approximate number of entries: {}", store.approximateNumEntries());
        var localResult = store.get(id);
        if (localResult != null) {
            log.info("Found result in local store: {}", localResult);
            return localResult;
        }
        throw new RuntimeException(String.format("No entry for key %s found", id));
    }


}

