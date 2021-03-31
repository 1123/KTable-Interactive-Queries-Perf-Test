package com.example.interactivequeries;

import lombok.Data;
import org.apache.kafka.streams.state.StreamsMetadata;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class StreamsMetaDataListPojo {

    Set<StreamsMetaDataPojo> streamsMetaDataPojoList;

    public StreamsMetaDataListPojo(Collection<StreamsMetadata> streamsMetaData) {
        this.streamsMetaDataPojoList = streamsMetaData.stream().map(StreamsMetaDataPojo::new).collect(Collectors.toSet());
    }
}


