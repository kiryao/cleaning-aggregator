package ru.otus.otuskotlin.cleaningaggregator.app.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import ru.otus.otuskotlin.cleaningaggregator.common.ClagCorSettings
import ru.otus.otuskotlin.cleaningaggregator.logging.common.LoggerProvider
import ru.otus.otuskotlin.cleaningaggregator.logging.jvm.loggerLogback

@Suppress("unused")
@Configuration
class OrderConfig {
    @Bean
    fun processor(corSettings: ClagCorSettings) = ClagOrderProcessor(corSettings = corSettings)

    @Bean
    fun loggerProvider(): LoggerProvider = LoggerProvider { loggerLogback(it) }

    @Bean
    fun corSettings(): ClagCorSettings = ClagCorSettings(
        loggerProvider = loggerProvider(),
    )

    @Bean
    fun appSettings(
        corSettings: ClagCorSettings,
        processor: ClagOrderProcessor,
    ) = ClagAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
