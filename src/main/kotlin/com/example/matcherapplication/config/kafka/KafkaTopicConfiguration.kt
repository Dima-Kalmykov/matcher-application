package com.example.matcherapplication.config.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.support.beans

private const val PARTITIONS_NUMBER = 1
private const val REPLICATION_FACTOR = 1

fun kafkaTopicConfiguration() = beans {
    bean {
        val topic = ref<KafkaProperties>().properties["topic"]

        NewTopic(topic, PARTITIONS_NUMBER, REPLICATION_FACTOR.toShort())
    }
}
