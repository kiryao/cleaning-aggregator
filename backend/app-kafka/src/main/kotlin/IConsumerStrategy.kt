package ru.otus.otuskotlin.cleaningaggregator.app.kafka

import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext

/**
 * Интерфейс стратегии для обслуживания версии API
 */
interface IConsumerStrategy {
    /**
     * Топики, для которых применяется стратегия
     */
    fun topics(config: AppKafkaConfig): InputOutputTopics
    /**
     * Сериализатор для версии API
     */
    fun serialize(source: ClagContext): String
    /**
     * Десериализатор для версии API
     */
    fun deserialize(value: String, target: ClagContext)
}
