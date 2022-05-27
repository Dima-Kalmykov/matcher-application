package com.example.matcherapplication.consumer

import com.example.matcherapplication.model.Publication
import com.example.matcherapplication.service.NotificationService
import com.example.matcherapplication.service.SubscriptionsService
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class KafkaPublicationsListener(
    private val subscriptionsService: SubscriptionsService,
    private val notificationService: NotificationService,
) {

    @KafkaListener(topics = ["\${spring.kafka.properties.topic}"])
    fun consume(message: Publication) {
        logger.info { "Received message: $message" }
        val users = subscriptionsService.getUsers(message.channelId)

        users.forEach { notificationService.notify(it) }
    }
}
