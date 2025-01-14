package ru.otus.otuskotlin.cleaningaggregator.app.spring.controllers

import org.springframework.web.bind.annotation.*
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers.*
import ru.otus.otuskotlin.cleaningaggregator.app.common.controllerHelper
import ru.otus.otuskotlin.cleaningaggregator.app.spring.config.ClagAppSettings
import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("v1/order")
class OrderControllerV1(private val appSettings: ClagAppSettings) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: OrderCreateRequest): OrderCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun read(@RequestBody request: OrderReadRequest): OrderReadResponse =
        process(appSettings, request = request, this::class, "read")

    @PostMapping("update")
    suspend fun update(@RequestBody request: OrderUpdateRequest): OrderUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun delete(@RequestBody request: OrderDeleteRequest): OrderDeleteResponse =
        process(appSettings, request = request, this::class, "delete")

    @PostMapping("publish")
    suspend fun publish(@RequestBody request: OrderPublishRequest): OrderPublishResponse =
        process(appSettings, request = request, this::class, "publish")

    @PostMapping("respond")
    suspend fun respond(@RequestBody request: OrderRespondRequest): OrderRespondResponse =
        process(appSettings, request = request, this::class, "respond")

    @PostMapping("search")
    suspend fun search(@RequestBody request: OrderSearchRequest): OrderSearchResponse =
        process(appSettings, request = request, this::class, "search")

    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: ClagAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            {
                fromTransport(request)
            },
            { toTransport() as R },
            clazz,
            logId,
        )
    }
}
