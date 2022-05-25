package com.example.matcherapplication.consumer

import com.example.matcherapplication.model.Publication
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class KafkaPublicationsListener {

    @KafkaListener(topics = ["\${spring.kafka.properties.topic}"])
    fun consume(message: Publication) {
        logger.info { "Received message: $message" }
    }
}
