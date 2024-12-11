package ru.otus.otuskotlin.cleaningaggregator.api.log.mapper

import kotlinx.datetime.*
import ru.otus.otuskotlin.cleaningaggregator.api.log.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

fun ClagContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ok-cleaningaggregator",
    order = toClagLog(),
    errors = errors.map { it.toLog() },
)

private fun ClagContext.toClagLog(): ClagOrderLogModel? {
    val orderNone = ClagOrder()

    return ClagOrderLogModel(
        requestId = requestId.takeIf { it != ClagRequestId.NONE }?.asString(),
        requestOrder = orderRequest.takeIf { it != orderNone }?.toLog(),
        responseOrder = orderResponse.takeIf { it != orderNone }?.toLog(),
        responseOrders = ordersResponse.takeIf { it.isNotEmpty() }?.filter { it != orderNone }?.map { it.toLog() },
        requestFilter = orderFilterRequest.takeIf { it != ClagOrderFilter() }?.toLog(),
    ).takeIf { it != ClagOrderLogModel() }
}

private fun ClagOrderFilter.toLog() = OrderFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    customerId = customerId.takeIf { it != ClagCustomerId.NONE }?.asString()
)

private fun ClagOrder.toLog() = OrderLog(
    id = id.takeIf { it != ClagOrderId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    specialRequirements = specialRequirements.takeIf { it.isNotBlank() },
    cleaningType = cleaningType.takeIf { it != ClagCleaningType.NONE }?.name,
    address = address.takeIf { it != ClagAddress() }?.toLog(),
    sheduledFor = sheduledFor.takeIf { it != Instant.NONE }?.toString(),
    cost = cost.takeIf { it != ClagCost() }?.toLog(),
    executorId = executorId.takeIf { it != ClagExecutorId.NONE }?.asString(),
    customerId = customerId.takeIf { it != ClagCustomerId.NONE }?.asString(),
    status = status.name,
    metadata = metadata.takeIf { it != ClagOrderMetadata() }?.toLog(),
    permissions = permissionsClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet(),
)

private fun ClagCost.toLog(): OrderLogCost = OrderLogCost(
    amount = amount.takeIf { !isZero() }?.toPlainString(),
    currency = currency.takeIf { it.isNotBlank() }
)

private fun ClagOrderMetadata.toLog(): OrderLogMetadata = OrderLogMetadata(
    createdAt = createdAt.takeIf { it != Instant.NONE }?.toString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.toString(),
    publishedAt = publishedAt.takeIf { it != Instant.NONE }?.toString(),
    respondedAt = respondedAt.takeIf { it != Instant.NONE }?.toString(),
    deletedAt = deletedAt.takeIf { it != Instant.NONE }?.toString(),
)

private fun ClagAddress.toLog(): OrderLogAddress = OrderLogAddress(
    city = city.takeIf { it.isNotBlank() },
    street = street.takeIf { it.isNotBlank() },
    house = house.takeIf { it.isNotBlank() },
    apartment = apartment.takeIf { it.isNotBlank() },
    floor = floor.takeIf { it >= 0 },
    entranceNumber = entranceNumber.takeIf { it.isNotBlank() }
)

private fun ClagError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)
