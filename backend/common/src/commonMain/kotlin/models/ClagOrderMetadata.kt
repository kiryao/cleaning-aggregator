package ru.otus.otuskotlin.cleaningaggregator.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

data class ClagOrderMetadata(
    var createdAt: Instant = Instant.NONE,
    var updatedAt: Instant = Instant.NONE,
    var publishedAt: Instant = Instant.NONE,
    var respondedAt: Instant = Instant.NONE,
    var deletedAt: Instant = Instant.NONE
)
