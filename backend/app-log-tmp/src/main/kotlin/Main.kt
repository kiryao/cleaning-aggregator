package ru.otus.otuskotlin.marketplace.app.log.tmp

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.delay
import kotlinx.datetime.*
import ru.otus.otuskotlin.cleaningaggregator.api.log.mapper.toLog
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.logging.common.LogLevel
import ru.otus.otuskotlin.cleaningaggregator.logging.jvm.loggerLogback

suspend fun main() {
    val logger = loggerLogback("app-tmp")
    while (true) {
        val context = ClagContext(
            requestId = ClagRequestId("tmp-request-id"),
            timeStart = Clock.System.now(),
            command = ClagCommand.CREATE,
            workMode = ClagWorkMode.PROD,
            orderResponse = ClagOrder(
                id = ClagOrderId("tmp-order-id"),
                title = "tmp-title",
                description = "tmp-description",
                cleaningType = ClagCleaningType.MAINTENANCE,
                sheduledFor = Clock.System.now(),
                specialRequirements = "tmp-special",
                address = ClagAddress(
                    city = "tmp-city",
                    street = "tmp-street",
                    house = "tmp-house",
                    apartment = "tmp-apartment",
                    floor = 1,
                    entranceNumber = "tmp-entrance",
                ),
                cost = ClagCost(
                    amount = BigDecimal.parseString("1054.9879817"),
                    currency = "RUB"
                ),
                customerId = ClagCustomerId("tmp-customer-id"),
                executorId = ClagExecutorId.NONE,
                permissionsClient = mutableSetOf(ClagOrderPermissionsClient.READ, ClagOrderPermissionsClient.DELETE),
                status = ClagOrderStatus.DRAFT,
                metadata = ClagOrderMetadata(
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now(),
                    publishedAt = Clock.System.now(),
                    respondedAt = Clock.System.now(),
                    deletedAt = Clock.System.now(),
                )
            ),
            state = ClagState.RUNNING,
            errors = mutableListOf(
                ClagError(
                    code = "tmp-error",
                    group = "tmp",
                    field = "none",
                    message = "tmp error message",
                    level = LogLevel.INFO,
                    exception = Exception("tmp some exception"),
                )
            )
        )
        logger.info(
            msg = "tmp log string",
            data = context.toLog("tmp-app-logg"),
        )
        delay(500)
    }
}
