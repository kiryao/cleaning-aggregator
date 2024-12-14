package ru.otus.otuskotlin.cleaningaggregator.common.models

data class ClagOrderFilter(
    var searchString: String = "",
    var customerId: ClagCustomerId = ClagCustomerId.NONE,
)
