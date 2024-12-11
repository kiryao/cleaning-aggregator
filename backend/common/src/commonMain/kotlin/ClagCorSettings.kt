package ru.otus.otuskotlin.cleaningaggregator.common

import ru.otus.otuskotlin.cleaningaggregator.logging.common.LoggerProvider

data class ClagCorSettings(
    val loggerProvider: LoggerProvider = LoggerProvider(),
) {
    companion object {
        val NONE = ClagCorSettings()
    }
}
