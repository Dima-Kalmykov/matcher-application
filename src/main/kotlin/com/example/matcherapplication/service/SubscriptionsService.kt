package com.example.matcherapplication.service

import com.example.matcherapplication.config.rest.RestProperties
import com.example.matcherapplication.model.SubscriptionWrapper
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder.fromUriString

private val logger = KotlinLogging.logger { }

@Service
class SubscriptionsService(
    private val restProperties: RestProperties,
    private val restTemplate: RestTemplate = RestTemplate(),
) {

    fun getUserEmails(channelName: String, userEmail: String) = withLog {
        restTemplate.getForObject(
            fromUriString(restProperties.url)
                .path(restProperties.resources.subscription)
                .queryParam("channelName", channelName)
                .queryParam("userEmail", userEmail)
                .toUriString(),
            SubscriptionWrapper::class.java,
        )?.subscriptions ?: emptyList()
    }

    private fun <T> withLog(function: () -> T): T {
        val subscription = "Subscriptions"

        logger.info { "$subscription.Request" }
        val result = function()
        logger.info { "$subscription.Response" }

        return result
    }
}
