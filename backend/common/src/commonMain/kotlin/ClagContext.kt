package ru.otus.otuskotlin.cleaningaggregator.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.models.filter.ClagOrderFilter
import ru.otus.otuskotlin.cleaningaggregator.common.stubs.ClagStubs

data class ClagContext (
    var command: ClagCommand = ClagCommand.NONE,
    var state: ClagState = ClagState.NONE,
    val errors: MutableList<ClagError> = mutableListOf(),

    var workMode: ClagWorkMode = ClagWorkMode.PROD,
    var stubCase: ClagStubs = ClagStubs.NONE,

    var requestId: ClagRequestId = ClagRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var orderRequest: ClagOrder = ClagOrder(),
    var orderFilterRequest: ClagOrderFilter = ClagOrderFilter(),

    var orderResponse: ClagOrder = ClagOrder(),
    var ordersResponse: MutableList<ClagOrder> = mutableListOf(),
)
