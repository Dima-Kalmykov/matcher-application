package com.example.matcherapplication.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.support.beans

fun jacksonConfiguration() = beans {
    bean {
        ObjectMapper().registerModule(
            KotlinModule.Builder()
                .build()
        )
    }
}
