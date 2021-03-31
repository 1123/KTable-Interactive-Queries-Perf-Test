package com.example.interactivequeries;

import lombok.Data;
import org.apache.kafka.streams.processor.ThreadMetadata;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AllThreadsMetaDataPojo {

    private final Set<ThreadMetadataPojo> threadMetaDataSet;

    public AllThreadsMetaDataPojo(Set<ThreadMetadata> threadMetadataSet) {
        this.threadMetaDataSet = threadMetadataSet.stream().map(ThreadMetadataPojo::new).collect(Collectors.toSet());
    }


}
