package ru.otus.otuskotlin.cleaningaggregator.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

/**
 * Инициализирует выбранный логер
 *
 * ```kotlin
 * // Обычно логер вызывается вот так
 * val logger = LoggerFactory.getLogger(this::class.java)
 * // Мы создаем экземпляр логер-провайдера вот так
 * val loggerProvider = LoggerProvider { clazz -> loggerLogback(clazz) }
 *
 * // В дальнейшем будем использовать этот экземпляр вот так:
 * val logger = loggerProvider.logger(this::class)
 * logger.info("My log")
 * ```
 */
class LoggerProvider(
    private val provider: (String) -> ILogWrapper = { ILogWrapper.DEFAULT }
) {
    /**
     * Инициализирует и возвращает экземпляр логера по идентификатору
     */
    fun logger(loggerId: String): ILogWrapper = provider(loggerId)

    /**
     * Инициализирует и возвращает экземпляр логера по классу
     */
    fun logger(clazz: KClass<*>): ILogWrapper = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")

    /**
     * Инициализирует и возвращает экземпляр логера по функции
     */
    fun logger(function: KFunction<*>): ILogWrapper = provider(function.name)
}