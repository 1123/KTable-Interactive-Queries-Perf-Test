package com.example.interactivequeries;

import lombok.Data;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.streams.processor.TaskMetadata;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class TaskPojo {

    private final String taskId;
    private Set<String> topicPartitionPojos;

    public TaskPojo(TaskMetadata taskMetadata) {
        this.taskId = taskMetadata.taskId();
        this.topicPartitionPojos = taskMetadata.topicPartitions().stream().map(TopicPartition::toString).collect(Collectors.toSet());
    }
}
