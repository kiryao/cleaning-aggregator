package ru.otus.otuskotlin.cleaningaggregator.app.common

import kotlinx.datetime.Clock
import ru.otus.otuskotlin.cleaningaggregator.api.log.mapper.toLog
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.common.helpers.asClagError
import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagState
import kotlin.reflect.KClass

suspend inline fun <T> IClagAppSettings.controllerHelper(
    crossinline getRequest: suspend ClagContext.() -> Unit,
    crossinline toResponse: suspend ClagContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = ClagContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.state = ClagState.FAILING
        ctx.errors.add(e.asClagError())
        processor.exec(ctx)
        ctx.toResponse()
    }
}
