package com.example.matcherapplication.service

import com.example.matcherapplication.model.User
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class NotificationService {

    fun notify(user: User) {
        logger.info { "Send notification to user with name ${user.name}" }
    }
}
