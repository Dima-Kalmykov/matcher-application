package com.example.matcherapplication.service

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class NotificationService {

    fun notify(email: String, channelName: String) {
        logger.info { "Send notification to user with email '$email' about new post in channel '$channelName'" }
    }
}
