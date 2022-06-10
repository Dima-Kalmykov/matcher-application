package com.example.matcherapplication.model

data class SubscriptionWrapper(val subscriptions: List<Subscription>)

data class Subscription(
    val channelName: String,
    val userEmail: String,
)
