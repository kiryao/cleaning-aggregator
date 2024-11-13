package ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.stubs.ClagStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlin.test.assertNull

class MapperPublishTest {

    @Test
    fun fromTransport() {
        val req = OrderPublishRequest(
            debug = OrderDebug(
                mode = OrderRequestDebugMode.STUB,
                stub = OrderRequestDebugStubs.SUCCESS
            ),
            order = OrderPublishObject(
                id = "1234",
                lock = "456"
            )
        )

        val context = ClagContext()
        context.fromTransport(req)

        assertEquals(ClagStubs.SUCCESS, context.stubCase)
        assertEquals("1234", context.orderRequest.id.asString())
        assertEquals("456", context.orderRequest.lock.asString())
    }

    @Test
    fun toTransport() {
        val testInstant = Instant.parse("2022-01-01T00:00:00Z")

        val context = ClagContext(
            requestId = ClagRequestId("1234"),
            command = ClagCommand.PUBLISH,
            workMode = ClagWorkMode.PROD,
            orderResponse = ClagOrder(
                id = ClagOrderId("1234"),
                title = "title",
                description = "description",
                cleaningType = ClagCleaningType.MAINTENANCE,
                sheduledFor = testInstant,
                specialRequirements = "special",
                address = ClagAddress(
                    city = "city",
                    street = "street",
                    house = "house",
                    apartment = "apartment",
                    floor = 1,
                    entranceNumber = "entrance",
                ),
                cost = ClagCost(
                    amount = BigDecimal.parseString("1054.98798179412787124142"),
                    currency = "RUB"
                ),
                customerId = ClagCustomerId("1234"),
                executorId = ClagExecutorId.NONE,
                permissionsClient = mutableSetOf(
                    ClagOrderPermissionsClient.READ,
                    ClagOrderPermissionsClient.DELETE,
                    ClagOrderPermissionsClient.UPDATE,
                    ClagOrderPermissionsClient.PUBLISH
                ),
                status = ClagOrderStatus.DRAFT,
                metadata = ClagOrderMetadata(
                    createdAt = testInstant,
                    updatedAt = testInstant,
                    publishedAt = testInstant,
                    respondedAt = testInstant,
                    deletedAt = testInstant,
                ),
                lock = ClagOrderLock("456")
            ),
            state = ClagState.RUNNING,
            errors = mutableListOf(
                ClagError(
                    group = "request",
                    code = "error",
                    message = "message",
                    field = "field"
                )
            )
        )

        val res = context.toTransport() as OrderPublishResponse

        assertEquals("1234", res.order?.id)
        assertEquals("title", res.order?.title)
        assertEquals("description", res.order?.description)
        assertEquals(CleaningType.MAINTENANCE, res.order?.cleaningType)
        assertEquals("2022-01-01T00:00:00Z", res.order?.sheduledFor)
        assertEquals("special", res.order?.specialRequirements)

        assertEquals("city", res.order?.address?.city)
        assertEquals("street", res.order?.address?.street)
        assertEquals("house", res.order?.address?.house)
        assertEquals("apartment", res.order?.address?.apartment)
        assertEquals(1, res.order?.address?.floor)
        assertEquals("entrance", res.order?.address?.entranceNumber)

        assertEquals("1054.98798179412787124142", res.order?.cost?.amount?.toString())
        assertEquals("RUB", res.order?.cost?.currency)

        assertEquals("1234", res.order?.customerId)
        assertNull(res.order?.executorId)

        assertEquals(OrderStatus.DRAFT, res.order?.status)

        assertEquals("456", res.order?.lock)

        assertEquals("2022-01-01T00:00:00Z", res.order?.metadata?.createdAt)
        assertEquals("2022-01-01T00:00:00Z", res.order?.metadata?.updatedAt)
        assertEquals("2022-01-01T00:00:00Z", res.order?.metadata?.publishedAt)
        assertEquals("2022-01-01T00:00:00Z", res.order?.metadata?.respondedAt)
        assertEquals("2022-01-01T00:00:00Z", res.order?.metadata?.deletedAt)

        assertEquals(4, res.order?.permissions?.size)
        assertEquals(OrderPermissions.READ, res.order?.permissions?.firstOrNull())
        assertEquals(OrderPermissions.PUBLISH, res.order?.permissions?.lastOrNull())

        assertEquals(ResponseResult.SUCCESS, res.result)

        assertEquals(1, res.errors?.size)
        assertEquals("request", res.errors?.firstOrNull()?.group)
        assertEquals("error", res.errors?.firstOrNull()?.code)
        assertEquals("message", res.errors?.firstOrNull()?.message)
        assertEquals("field", res.errors?.firstOrNull()?.field)
    }
}
