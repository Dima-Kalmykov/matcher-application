package com.example.matcherapplication.config.rest

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConstructorBinding
@ConfigurationProperties(prefix = "rest.subscriptions-api")
class RestProperties(
    val url: String,
    @NestedConfigurationProperty val resources: SubscriptionsResources = SubscriptionsResources(),
)

@ConstructorBinding
data class SubscriptionsResources(
    val users: String = "/users",
)
