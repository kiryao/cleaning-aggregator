package ru.otus.otuskotlin.cleaningaggregator.api.v1

import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestV1SerializationTest {
    private val request: IRequest = OrderCreateRequest(
        debug = OrderDebug(
            mode = OrderRequestDebugMode.STUB,
            stub = OrderRequestDebugStubs.NOT_ENOUGH_PERMISSIONS
        ),
        order = OrderCreateObject(
            cleaningType = CleaningType.GENERAL,
            sheduledFor = "2024-10-29T00:00:00Z",
            address = OrderAddress(
                city = "Moscow",
                street = "Lenina",
                house = "15",
                apartment = "1"
            )
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.encodeToString(IRequest.serializer(), request)

        assertContains(json, Regex("\"cleaningType\":\\s*\"general\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"not_enough_permissions\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(request)
        val obj = apiV1Mapper.decodeFromString<IRequest>(json) as OrderCreateRequest

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"order": null}
        """.trimIndent()
        val obj = apiV1Mapper.decodeFromString<OrderCreateRequest>(jsonString)

        assertEquals(null, obj.order)
    }
}
