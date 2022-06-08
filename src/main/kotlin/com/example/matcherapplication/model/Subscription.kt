package com.example.matcherapplication.model

data class SubscriptionWrapper(val subscriptions: List<Subscription>)

data class Subscription(
    val channel: Channel,
    val user: User,
)

data class Channel(val name: String)

data class User(val email: String)
