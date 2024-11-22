package ru.otus.otuskotlin.cleaningaggregator.common.models.filter

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

data class ClagDateRange(
    var start: Instant = Instant.NONE,
    var end: Instant = Instant.NONE
)
