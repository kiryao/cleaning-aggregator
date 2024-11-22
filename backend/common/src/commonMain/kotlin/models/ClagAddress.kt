package ru.otus.otuskotlin.cleaningaggregator.common.models

data class ClagAddress(
    var city: String = "",
    var street: String = "",
    var house: String = "",
    var apartment: String = "",
    var floor: Int = 0,
    var entranceNumber: String = "",
)
