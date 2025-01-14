package ru.otus.otuskotlin.cleaningaggregator.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import ru.otus.otuskotlin.cleaningaggregator.api.v1.apiV1Mapper

@Suppress("unused")
@Configuration
class SerializationConfiguration {
    @Bean
    fun messageConverter(): KotlinSerializationJsonHttpMessageConverter {
        return KotlinSerializationJsonHttpMessageConverter(apiV1Mapper)
    }
}
