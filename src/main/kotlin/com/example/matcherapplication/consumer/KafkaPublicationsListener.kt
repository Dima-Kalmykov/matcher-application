package com.example.matcherapplication.consumer

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
    fun consume(channelName: String) {
        logger.info { "Received message: $channelName" }
        val subscriptions = subscriptionsService.getUserEmails(channelName = channelName, userEmail = "")

        subscriptions.forEach {
            notificationService.notify(it.userEmail, it.channelName)
        }
    }
}
