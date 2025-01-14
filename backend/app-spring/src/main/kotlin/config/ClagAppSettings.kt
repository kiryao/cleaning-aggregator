package ru.otus.otuskotlin.cleaningaggregator.app.spring.config

import ru.otus.otuskotlin.cleaningaggregator.app.common.IClagAppSettings
import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import ru.otus.otuskotlin.cleaningaggregator.common.ClagCorSettings

data class ClagAppSettings(
    override val corSettings: ClagCorSettings,
    override val processor: ClagOrderProcessor,
): IClagAppSettings
