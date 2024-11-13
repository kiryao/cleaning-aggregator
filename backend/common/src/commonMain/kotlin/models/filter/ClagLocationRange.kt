package ru.otus.otuskotlin.cleaningaggregator.common.models.filter

data class ClagLocationRange(
    var city: String = "",
    var street: String = "",
    var house: String = "",
    var radius: Double = 0.0
)
