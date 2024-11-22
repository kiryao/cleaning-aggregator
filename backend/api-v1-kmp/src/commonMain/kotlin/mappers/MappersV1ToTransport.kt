package ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers

import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.exceptions.UnknownClagCommand
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

fun ClagContext.toTransport(): IResponse = when (val cmd = command) {
    ClagCommand.CREATE -> toTransportCreate()
    ClagCommand.READ -> toTransportRead()
    ClagCommand.UPDATE -> toTransportUpdate()
    ClagCommand.DELETE -> toTransportDelete()
    ClagCommand.PUBLISH -> toTransportPublish()
    ClagCommand.RESPOND -> toTransportRespond()
    ClagCommand.SEARCH -> toTransportSearch()
    ClagCommand.NONE -> throw UnknownClagCommand(cmd)
}

fun ClagContext.toTransportCreate() = OrderCreateResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportRead() = OrderReadResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportUpdate() = OrderUpdateResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportDelete() = OrderDeleteResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportPublish() = OrderPublishResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportRespond() = OrderRespondResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    order = orderResponse.toTransportOrder()
)

fun ClagContext.toTransportSearch() = OrderSearchResponse(
    result = state.toTransportResult(),
    errors = errors.toTransportErrors(),
    orders = ordersResponse.toTransportOrders()
)

private fun ClagOrder.toTransportOrder(): OrderResponseObject = OrderResponseObject(
    id = id.takeIf { it != ClagOrderId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    cleaningType = cleaningType.toExternal(),
    sheduledFor = sheduledFor.takeIf { it != Instant.NONE }?.toString(),
    specialRequirements = specialRequirements.takeIf { it.isNotBlank() },
    address = address.toExternal(),
    customerId = customerId.takeIf { it != ClagCustomerId.NONE }?.asString(),
    executorId = executorId.takeIf { it != ClagExecutorId.NONE }?.asString(),
    cost = cost.toExternal(),
    permissions = permissionsClient.toExternal(),
    status = status.toExternal(),
    metadata = metadata.toExternal(),
    lock = lock.takeIf { it != ClagOrderLock.NONE }?.asString()
)

private fun List<ClagOrder>.toTransportOrders(): List<OrderResponseObject>? = this
    .map { it.toTransportOrder() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ClagCleaningType.toExternal(): CleaningType? = when (this) {
    ClagCleaningType.MAINTENANCE -> CleaningType.MAINTENANCE
    ClagCleaningType.GENERAL -> CleaningType.GENERAL
    ClagCleaningType.NONE -> null
}

private fun ClagAddress.toExternal(): OrderAddress = OrderAddress(
    city = city.takeIf { it.isNotBlank() },
    street = street.takeIf { it.isNotBlank() },
    house = house.takeIf { it.isNotBlank() },
    apartment = apartment.takeIf { it.isNotBlank() },
    floor = floor.takeIf { it >= 0 },
    entranceNumber = entranceNumber.takeIf { it.isNotBlank() }
)

private fun ClagCost.toExternal(): OrderCost = OrderCost(
    amount = amount.takeIf { !isZero() }?.toPlainString(),
    currency = currency.takeIf { it.isNotBlank() }
)

private fun ClagOrderMetadata.toExternal(): OrderMetadata = OrderMetadata(
    createdAt = createdAt.takeIf { it != Instant.NONE }?.toString(),
    updatedAt = updatedAt.takeIf { it != Instant.NONE }?.toString(),
    publishedAt = publishedAt.takeIf { it != Instant.NONE }?.toString(),
    respondedAt = respondedAt.takeIf { it != Instant.NONE }?.toString(),
    deletedAt = deletedAt.takeIf { it != Instant.NONE }?.toString(),
)

private fun ClagOrderStatus.toExternal(): OrderStatus? = when (this) {
    ClagOrderStatus.DRAFT -> OrderStatus.DRAFT
    ClagOrderStatus.PUBLISHED -> OrderStatus.PUBLISHED
    ClagOrderStatus.RESPONDED -> OrderStatus.RESPONDED
    ClagOrderStatus.DELETED -> OrderStatus.DELETED
}

private fun ClagState.toTransportResult(): ResponseResult? = when (this) {
    ClagState.RUNNING -> ResponseResult.SUCCESS
    ClagState.FAILING -> ResponseResult.ERROR
    ClagState.FINISHING -> ResponseResult.SUCCESS
    ClagState.NONE -> null
}

private fun ClagOrderPermissionsClient.toExternal(): OrderPermissions = when (this) {
    ClagOrderPermissionsClient.CREATE -> OrderPermissions.CREATE
    ClagOrderPermissionsClient.READ -> OrderPermissions.READ
    ClagOrderPermissionsClient.UPDATE -> OrderPermissions.UPDATE
    ClagOrderPermissionsClient.DELETE -> OrderPermissions.DELETE
    ClagOrderPermissionsClient.PUBLISH -> OrderPermissions.PUBLISH
    ClagOrderPermissionsClient.RESPOND -> OrderPermissions.RESPOND
}

private fun Set<ClagOrderPermissionsClient>.toExternal(): Set<OrderPermissions>? = this
    .map { it.toExternal() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun List<ClagError>.toTransportErrors(): List<Error>? = this
    .map { it.toExternal() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun ClagError.toExternal() = Error(
    group = group.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
)
