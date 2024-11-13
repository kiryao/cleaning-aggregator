package ru.otus.otuskotlin.cleaningaggregator.common.models

data class ClagError(
    val group: String = "",
    val code: String = "",
    val message: String = "",
    val field: String = "",
    val exception: Throwable? = null,
)
