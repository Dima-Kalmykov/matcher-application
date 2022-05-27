package com.example.matcherapplication

import com.example.matcherapplication.config.jacksonConfiguration
import com.example.matcherapplication.config.kafka.kafkaConsumerConfiguration
import com.example.matcherapplication.config.kafka.kafkaTopicConfiguration
import com.example.matcherapplication.config.rest.RestProperties
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RestProperties::class)
class MatcherApplication

fun main(args: Array<String>) {
    runApplication<MatcherApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
        addInitializers(
            jacksonConfiguration(),
            kafkaTopicConfiguration(),
            kafkaConsumerConfiguration(),
        )
    }
}
