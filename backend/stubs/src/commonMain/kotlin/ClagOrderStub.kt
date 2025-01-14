package ru.otus.otuskotlin.cleaningaggregator.stubs

import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.stubs.ClagOrderStubValue.ORDER_MAINTENANCE

object ClagOrderStub {
    fun get(): ClagOrder = ORDER_MAINTENANCE.copy()

    fun prepareResult(block: ClagOrder.() -> Unit): ClagOrder = get().apply(block)

    fun prepareSearchList(filter: String): List<ClagOrder> {
        return listOf(
            clagOrder("1", filter),
            clagOrder("2", filter),
            clagOrder("3", filter),
            clagOrder("4", filter),
            clagOrder("5", filter)
        )
    }

    private fun clagOrder(id: String, filter: String): ClagOrder {
        return ORDER_MAINTENANCE.copy(
            id = ClagOrderId(id),
            title = "$filter $id",
            description = "Описание $filter $id"
        )
    }
}
