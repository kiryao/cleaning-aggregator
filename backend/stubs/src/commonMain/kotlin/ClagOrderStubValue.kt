package ru.otus.otuskotlin.cleaningaggregator.stubs

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

object ClagOrderStubValue {
    val ORDER_MAINTENANCE: ClagOrder
        get() = ClagOrder(
            id = ClagOrderId("10"),
            title = "Поддерживающая уборка в Москве",
            description = "Нужен качественный исполнитель на долгое время, буду делать повторные заказы",
            specialRequirements = "Требуется, чтобы исполнитель принёс тряпку",
            cleaningType = ClagCleaningType.MAINTENANCE,
            address = ClagAddress(
                city = "Москва",
                street = "Пушкина",
                house = "1",
                apartment = "10",
                floor = 2,
                entranceNumber = "1",
            ),
            sheduledFor = Instant.parse("2024-12-01T14:30:00Z"),
            cost = ClagCost(
                amount = BigDecimal.parseString("2000"),
                currency = "RUB"
            ),
            executorId = ClagExecutorId.NONE,
            customerId = ClagCustomerId("12"),
            permissionsClient = mutableSetOf(
                ClagOrderPermissionsClient.READ,
                ClagOrderPermissionsClient.DELETE
            ),
            status = ClagOrderStatus.PUBLISHED,
            metadata = ClagOrderMetadata(
                createdAt = Instant.parse("2024-12-01T08:00:00Z"),
                updatedAt = Instant.parse("2024-12-01T08:00:00Z"),
                publishedAt = Instant.parse("2024-12-01T08:05:00Z"),
                respondedAt = Instant.NONE,
                deletedAt = Instant.NONE,
            )
        )
}
