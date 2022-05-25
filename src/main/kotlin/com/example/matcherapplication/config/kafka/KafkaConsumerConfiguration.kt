package com.example.matcherapplication.config.kafka


import com.example.matcherapplication.model.Publication
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.support.beans
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer
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
            JsonDeserializer.VALUE_DEFAULT_TYPE to Publication::class.java,
            JsonDeserializer.USE_TYPE_INFO_HEADERS to false,
        )

        val errorHandlerDeserializer = ErrorHandlingDeserializer<Publication>(
            JsonDeserializer()
        )

        DefaultKafkaConsumerFactory(
            consumerProperties,
            StringDeserializer(),
            errorHandlerDeserializer,
        )
    }

    bean("kafkaListenerContainerFactory") {
        ConcurrentKafkaListenerContainerFactory<String, Publication>().apply {
            consumerFactory = ref<ConsumerFactory<String, Publication>>()
            setCommonErrorHandler(
                DefaultErrorHandler(
                    FixedBackOff(RETRY_INTERVAL, MAX_ATTEMPTS),
                ),
            )
        }
    }
}
