package ru.otus.otuskotlin.cleaningaggregator.app.common

import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import ru.otus.otuskotlin.cleaningaggregator.common.ClagCorSettings

interface IClagAppSettings {
    val corSettings: ClagCorSettings
    val processor: ClagOrderProcessor
}
