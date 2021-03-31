package com.example.interactivequeries;

import lombok.Data;
import org.apache.kafka.streams.processor.ThreadMetadata;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ThreadMetadataPojo {

    private final String threadName;
    private final String threadState;
    private Set<TaskPojo> activeTaskPojoList;
    private Set<TaskPojo> standByTaskPojoList;
    private final String adminClientId;
    private final String consumerClientId;
    private final Set<String> producerClientIds;
    private final String restoreConsumerClientId;

    public ThreadMetadataPojo(ThreadMetadata threadMetadata) {
        this.threadName = threadMetadata.threadName();
        this.threadState = threadMetadata.threadState();
        this.activeTaskPojoList = threadMetadata.activeTasks().stream().map(TaskPojo::new).collect(Collectors.toSet());
        this.standByTaskPojoList = threadMetadata.standbyTasks().stream().map(TaskPojo::new).collect(Collectors.toSet());
        this.adminClientId = threadMetadata.adminClientId();
        this.consumerClientId = threadMetadata.consumerClientId();
        this.producerClientIds = threadMetadata.producerClientIds();
        this.restoreConsumerClientId = threadMetadata.restoreConsumerClientId();
    }

}
