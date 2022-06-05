package com.example.matcherapplication.config.kafka


import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.support.beans
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

private const val MAX_ATTEMPTS = 1L
private const val RETRY_INTERVAL = 0L

fun kafkaConsumerConfiguration() = beans {
    bean("consumerFactory") {
        val kafkaProperties = ref<KafkaProperties>()

        val consumerProperties = mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId,
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.consumer.bootstrapServers,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaProperties.consumer.autoOffsetReset,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
        )

        DefaultKafkaConsumerFactory<String, String>(consumerProperties)
    }

    bean("kafkaListenerContainerFactory") {
        ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = ref<ConsumerFactory<String, String>>()
            setCommonErrorHandler(
                DefaultErrorHandler(
                    FixedBackOff(RETRY_INTERVAL, MAX_ATTEMPTS),
                ),
            )
        }
    }
}
