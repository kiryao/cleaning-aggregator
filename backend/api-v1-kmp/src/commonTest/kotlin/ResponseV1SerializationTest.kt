package ru.otus.otuskotlin.cleaningaggregator.api.v1

import kotlinx.serialization.encodeToString
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseV1SerializationTest {
    private val response: IResponse = OrderCreateResponse(
        order = OrderResponseObject(
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
        val json = apiV1Mapper.encodeToString(response)

        assertContains(json, Regex("\"cleaningType\":\\s*\"general\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.encodeToString(response)
        val obj = apiV1Mapper.decodeFromString<IResponse>(json) as OrderCreateResponse

        assertEquals(response, obj)
    }
}
