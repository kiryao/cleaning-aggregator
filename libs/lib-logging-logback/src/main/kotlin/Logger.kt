package ru.otus.otuskotlin.cleaningaggregator.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.cleaningaggregator.logging.common.ILogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal LogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun loggerLogback(logger: Logger): ILogWrapper = LogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

/**
 * Создаёт логгер на основе класса
 */
fun loggerLogback(clazz: KClass<*>): ILogWrapper = loggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

/**
 * Создаёт логгер на основе id
 */
@Suppress("unused")
fun loggerLogback(loggerId: String): ILogWrapper = loggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
