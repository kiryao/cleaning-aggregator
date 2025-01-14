package ru.otus.otuskotlin.cleaningaggregator.app.common

import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import ru.otus.otuskotlin.cleaningaggregator.common.*
import ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class ControllerV1Test {
    private val request = OrderCreateRequest(
        order = OrderCreateObject(
            description = "description",
            cleaningType = CleaningType.MAINTENANCE,
            sheduledFor = "2022-01-01T00:00:00Z",
            specialRequirements = "special",
            address = OrderAddress(
                city = "city",
                street = "street",
                house = "house",
                apartment = "apartment",
                floor = 1,
                entranceNumber = "entrance",
            )
        ),
        debug = OrderDebug(mode = OrderRequestDebugMode.STUB, stub = OrderRequestDebugStubs.SUCCESS)
    )

    private val appSettings: IClagAppSettings = object : IClagAppSettings {
        override val corSettings: ClagCorSettings = ClagCorSettings()
        override val processor: ClagOrderProcessor = ClagOrderProcessor(corSettings)
    }

    private suspend fun createOrderSpring(request: OrderCreateRequest): OrderCreateResponse =
        appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransport() as OrderCreateResponse },
            ControllerV1Test::class,
            "controller-v1-test"
        )

    @Test
    fun springHelperTest() = runTest {
        val res = createOrderSpring(request)
        assertEquals(ResponseResult.SUCCESS, res.result)
    }
}
