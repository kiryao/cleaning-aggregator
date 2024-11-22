package ru.otus.otuskotlin.cleaningaggregator.common.models.filter

import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagCleaningType

data class ClagOrderFilter (
    var cleaningType: ClagCleaningType = ClagCleaningType.NONE,
    var dateRange: ClagDateRange = ClagDateRange(),
    var locationRange: ClagLocationRange = ClagLocationRange()
)
