package com.example.interactivequeries;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StateStoreQueryService {

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @GetMapping("/store/{id}")
    public Integer getSomeKey(@PathVariable String id) {
        ReadOnlyKeyValueStore<String, Integer> store =
                streamsBuilderFactoryBean.getKafkaStreams().store("processed-messages-store", QueryableStoreTypes.keyValueStore());
        return store.get(id);
    }

}
