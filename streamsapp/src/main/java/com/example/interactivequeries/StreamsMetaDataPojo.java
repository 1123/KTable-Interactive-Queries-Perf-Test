package com.example.interactivequeries;

import lombok.Data;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.state.StreamsMetadata;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class StreamsMetaDataPojo {
    private final String hostinfo;
    private final Set<String> stateStoreNames;
    private final Set<String> topicPartitions;
    private final Set<String> standbyStateStoreNames;
    private final Set<String> standbyPartitions;

    public StreamsMetaDataPojo(StreamsMetadata streamsMetadata) {
        this.hostinfo = streamsMetadata.hostInfo().toString();
        this.standbyStateStoreNames = streamsMetadata.standbyStateStoreNames();
        this.standbyPartitions = streamsMetadata.standbyTopicPartitions().stream().map(TopicPartition::toString).collect(Collectors.toSet());
        this.topicPartitions = streamsMetadata.topicPartitions().stream().map(TopicPartition::toString).collect(Collectors.toSet());
        this.stateStoreNames = streamsMetadata.stateStoreNames();
    }
}
