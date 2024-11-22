package ru.otus.otuskotlin.cleaningaggregator.common.models

import ru.otus.otuskotlin.cleaningaggregator.logging.common.LogLevel

data class ClagError(
    val group: String = "",
    val code: String = "",
    val message: String = "",
    val field: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null,
)
