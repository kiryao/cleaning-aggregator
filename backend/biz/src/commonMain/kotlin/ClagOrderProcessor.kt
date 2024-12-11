package ru.otus.otuskotlin.cleaningaggregator.biz

import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.ClagCorSettings
import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagState
import ru.otus.otuskotlin.cleaningaggregator.stubs.ClagOrderStub

@Suppress("unused", "RedundantSuspendModifier")
class ClagOrderProcessor(val corSettings: ClagCorSettings) {
    suspend fun exec(ctx: ClagContext) {
        ctx.orderResponse = ClagOrderStub.get()
        ctx.ordersResponse = ClagOrderStub.prepareSearchList("order search").toMutableList()
        ctx.state = ClagState.RUNNING
    }
}
