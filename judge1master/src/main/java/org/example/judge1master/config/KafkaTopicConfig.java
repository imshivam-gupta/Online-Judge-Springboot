package org.example.judge1master.config;

import constants.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {
    public NewTopic submissionTopic() {
        return TopicBuilder.name(KafkaConstants.SUBMISSION_QUEUE)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

