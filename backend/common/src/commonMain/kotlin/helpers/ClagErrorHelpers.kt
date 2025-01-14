package ru.otus.otuskotlin.cleaningaggregator.common.helpers

import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagError

fun Throwable.asClagError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = ClagError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
