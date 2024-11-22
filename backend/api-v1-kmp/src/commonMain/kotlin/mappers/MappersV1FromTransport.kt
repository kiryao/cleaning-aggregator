package ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers

import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.models.filter.*
import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagWorkMode
import ru.otus.otuskotlin.cleaningaggregator.common.stubs.ClagStubs
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

fun ClagContext.fromTransport(request: IRequest) = when (request) {
    is OrderCreateRequest -> fromTransport(request)
    is OrderReadRequest -> fromTransport(request)
    is OrderUpdateRequest -> fromTransport(request)
    is OrderDeleteRequest -> fromTransport(request)
    is OrderPublishRequest -> fromTransport(request)
    is OrderRespondRequest -> fromTransport(request)
    is OrderSearchRequest -> fromTransport(request)
}

fun ClagContext.fromTransport(request: OrderCreateRequest) {
    command = ClagCommand.CREATE
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderReadRequest) {
    command = ClagCommand.READ
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderUpdateRequest) {
    command = ClagCommand.UPDATE
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderDeleteRequest) {
    command = ClagCommand.DELETE
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderPublishRequest) {
    command = ClagCommand.PUBLISH
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderRespondRequest) {
    command = ClagCommand.RESPOND
    orderRequest = request.order?.toInternal() ?: ClagOrder()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun ClagContext.fromTransport(request: OrderSearchRequest) {
    command = ClagCommand.SEARCH
    orderFilterRequest = request.orderFilter?.toInternal() ?: ClagOrderFilter()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun String?.toOrderId() = this?.let { ClagOrderId(it) } ?: ClagOrderId.NONE
private fun String?.toOrderLock() = this?.let { ClagOrderLock(it) } ?: ClagOrderLock.NONE
private fun String?.toExecutorId() = this?.let { ClagExecutorId(it) } ?: ClagExecutorId.NONE
private fun String?.toInstant(): Instant {
    return this?.let { inputString ->
        try {
            Instant.parse(inputString)
        } catch (_: Exception) {
            Instant.NONE
        }
    } ?: Instant.NONE
}

private fun OrderDebug?.transportToWorkMode(): ClagWorkMode = when (this?.mode) {
    OrderRequestDebugMode.PROD -> ClagWorkMode.PROD
    OrderRequestDebugMode.TEST -> ClagWorkMode.TEST
    OrderRequestDebugMode.STUB -> ClagWorkMode.STUB
    null -> ClagWorkMode.PROD
}

private fun OrderDebug?.transportToStubCase(): ClagStubs = when (this?.stub) {
    OrderRequestDebugStubs.SUCCESS -> ClagStubs.SUCCESS
    OrderRequestDebugStubs.BAD_ID -> ClagStubs.BAD_ID
    OrderRequestDebugStubs.BAD_CLEANING_TYPE -> ClagStubs.BAD_CLEANING_TYPE
    OrderRequestDebugStubs.BAD_SCHEDULED_DATE -> ClagStubs.BAD_SCHEDULED_DATE
    OrderRequestDebugStubs.BAD_DESCRIPTION -> ClagStubs.BAD_DESCRIPTION
    OrderRequestDebugStubs.BAD_SPECIAL_REQUIREMENTS -> ClagStubs.BAD_SPECIAL_REQUIREMENTS
    OrderRequestDebugStubs.BAD_ADDRESS -> ClagStubs.BAD_ADDRESS
    OrderRequestDebugStubs.BAD_SEARCH_CLEANING_TYPE -> ClagStubs.BAD_SEARCH_CLEANING_TYPE
    OrderRequestDebugStubs.BAD_SEARCH_RADIUS -> ClagStubs.BAD_SEARCH_RADIUS
    OrderRequestDebugStubs.BAD_DATE_RANGE -> ClagStubs.BAD_DATE_RANGE
    OrderRequestDebugStubs.ORDER_NOT_FOUND -> ClagStubs.ORDER_NOT_FOUND
    OrderRequestDebugStubs.ORDER_ALREADY_PUBLISHED -> ClagStubs.ORDER_ALREADY_PUBLISHED
    OrderRequestDebugStubs.ORDER_ALREADY_RESPONDED -> ClagStubs.ORDER_ALREADY_RESPONDED
    OrderRequestDebugStubs.ORDER_ALREADY_DELETED -> ClagStubs.ORDER_ALREADY_DELETED
    OrderRequestDebugStubs.NOT_ENOUGH_PERMISSIONS -> ClagStubs.NOT_ENOUGH_PERMISSIONS
    OrderRequestDebugStubs.BAD_LOCK_VERSION -> ClagStubs.BAD_LOCK_VERSION
    OrderRequestDebugStubs.DATABASE_ERROR -> ClagStubs.DATABASE_ERROR
    null -> ClagStubs.NONE
}

private fun OrderCreateObject.toInternal(): ClagOrder = ClagOrder(
    description = this.description ?: "",
    cleaningType = this.cleaningType.toInternal(),
    sheduledFor = this.sheduledFor.toInstant(),
    specialRequirements = this.specialRequirements ?: "",
    address = this.address?.toInternal() ?: ClagAddress(),
)

private fun OrderReadObject?.toInternal(): ClagOrder {
    if (this == null) return ClagOrder()

    return ClagOrder(id = this.id.toOrderId())
}

private fun OrderUpdateObject.toInternal(): ClagOrder = ClagOrder(
    id = this.id.toOrderId(),
    description = this.description ?: "",
    cleaningType = this.cleaningType.toInternal(),
    sheduledFor = this.sheduledFor.toInstant(),
    specialRequirements = this.specialRequirements ?: "",
    address = this.address?.toInternal() ?: ClagAddress(),
    lock = this.lock.toOrderLock(),
)

private fun OrderDeleteObject?.toInternal(): ClagOrder {
    if (this == null) return ClagOrder()

    return ClagOrder(
        id = this.id.toOrderId(),
        lock = this.lock.toOrderLock(),
    )
}

private fun OrderPublishObject?.toInternal(): ClagOrder {
    if (this == null) return ClagOrder()

    return ClagOrder(
        id = this.id.toOrderId(),
        lock = this.lock.toOrderLock(),
    )
}

private fun OrderRespondObject?.toInternal(): ClagOrder {
    if (this == null) return ClagOrder()

    return ClagOrder(
        id = this.id.toOrderId(),
        lock = this.lock.toOrderLock(),
        executorId = this.executorId.toExecutorId(),
    )
}

private fun CleaningType?.toInternal(): ClagCleaningType = when (this) {
    CleaningType.GENERAL -> ClagCleaningType.GENERAL
    CleaningType.MAINTENANCE -> ClagCleaningType.MAINTENANCE
    null -> ClagCleaningType.NONE
}

private fun OrderSearchFilter?.toInternal(): ClagOrderFilter = ClagOrderFilter(
    cleaningType = this?.cleaningType?.toInternal() ?: ClagCleaningType.NONE,
    dateRange = ClagDateRange(
        start = this?.dateRange?.start?.toInstant() ?: Instant.NONE,
        end = this?.dateRange?.end?.toInstant() ?: Instant.NONE
    ),
    locationRange = ClagLocationRange(
        city = this?.locationRange?.city ?: "",
        street = this?.locationRange?.street ?: "",
        house = this?.locationRange?.house ?: "",
        radius = this?.locationRange?.radius ?: 0.0
    )
)

private fun OrderAddress?.toInternal(): ClagAddress = ClagAddress(
    city = this?.city ?: "",
    street = this?.street ?: "",
    house = this?.house ?: "",
    apartment = this?.apartment ?: "",
    floor = this?.floor ?: 0,
    entranceNumber = this?.entranceNumber ?: "",
)
