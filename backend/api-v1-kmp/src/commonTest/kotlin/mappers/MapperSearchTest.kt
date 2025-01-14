package ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.stubs.ClagStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class MapperSearchTest {

    @Test
    fun fromTransport() {
        val req = OrderSearchRequest(
            debug = OrderDebug(
                mode = OrderRequestDebugMode.STUB,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            orderFilter = OrderSearchFilter(
                searchString = "test"
            )
        )

        val context = ClagContext()
        context.fromTransport(req)

        assertEquals(ClagStubs.SUCCESS, context.stubCase)
        assertEquals("test", context.orderFilterRequest.searchString)
    }

    @Test
    fun toTransport() {
        val context = ClagContext(
            requestId = ClagRequestId("1234"),
            command = ClagCommand.SEARCH,
            workMode = ClagWorkMode.PROD,
            ordersResponse = mutableListOf(
                ClagOrder(
                    id = ClagOrderId("1234"),
                    cleaningType = ClagCleaningType.MAINTENANCE,
                    sheduledFor = Instant.parse("2022-01-01T00:00:00Z"),
                    permissionsClient = mutableSetOf(
                        ClagOrderPermissionsClient.READ,
                        ClagOrderPermissionsClient.DELETE
                    ),
                    status = ClagOrderStatus.RESPONDED
                ),
                ClagOrder(
                    id = ClagOrderId("5678"),
                    cleaningType = ClagCleaningType.GENERAL,
                    sheduledFor = Instant.parse("2022-01-02T00:00:00Z"),
                    permissionsClient = mutableSetOf(
                        ClagOrderPermissionsClient.UPDATE,
                        ClagOrderPermissionsClient.PUBLISH,
                        ClagOrderPermissionsClient.READ
                    ),
                    status = ClagOrderStatus.PUBLISHED
                )
            ),
            state = ClagState.RUNNING,
            errors = mutableListOf(
                ClagError(group = "request", code = "error", message = "message", field = "field")
            )
        )

        val res = context.toTransport() as OrderSearchResponse

        assertEquals("1234", res.orders?.first()?.id)
        assertEquals(CleaningType.MAINTENANCE, res.orders?.first()?.cleaningType)
        assertEquals("2022-01-01T00:00:00Z", res.orders?.first()?.sheduledFor)
        assertEquals(2, res.orders?.first()?.permissions?.size)
        assertEquals(OrderPermissions.READ, res.orders?.first()?.permissions?.first())
        assertEquals(OrderPermissions.DELETE, res.orders?.first()?.permissions?.last())
        assertEquals(OrderStatus.RESPONDED, res.orders?.first()?.status)

        assertEquals("5678", res.orders?.last()?.id)
        assertEquals(CleaningType.GENERAL, res.orders?.last()?.cleaningType)
        assertEquals("2022-01-02T00:00:00Z", res.orders?.last()?.sheduledFor)
        assertEquals(3, res.orders?.last()?.permissions?.size)
        assertEquals(OrderPermissions.UPDATE, res.orders?.last()?.permissions?.first())
        assertEquals(OrderPermissions.READ, res.orders?.last()?.permissions?.last())
        assertEquals(OrderStatus.PUBLISHED, res.orders?.last()?.status)
    }
}
