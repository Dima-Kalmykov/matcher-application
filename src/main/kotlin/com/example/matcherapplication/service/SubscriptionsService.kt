package com.example.matcherapplication.service

import com.example.matcherapplication.config.rest.RestProperties
import com.example.matcherapplication.model.User
import mu.KotlinLogging
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder.fromUriString

private val logger = KotlinLogging.logger { }

@Service
class SubscriptionsService(
    private val restProperties: RestProperties,
    private val restTemplate: RestTemplate = RestTemplate(),
) {

    fun getUsers(channelId: String): List<User> {
        return withLog {
            checkNotNull(
                restTemplate.exchange(
                    fromUriString(restProperties.url)
                        .path(restProperties.resources.users)
                        .queryParam("channelId", channelId)
                        .toUriString(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    object : ParameterizedTypeReference<List<User>>() {}
                ).body
            )
        }
    }

    private fun <T> withLog(function: () -> T): T {
        val subscription = "Subscriptions"

        logger.info { "$subscription.Request" }
        val result = function()
        logger.info { "$subscription.Response" }

        return result
    }
}
