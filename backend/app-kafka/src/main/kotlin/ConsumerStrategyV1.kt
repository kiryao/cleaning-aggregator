package ru.otus.otuskotlin.cleaningaggregator.app.kafka

import ru.otus.otuskotlin.cleaningaggregator.api.v1.apiV1RequestDeserialize
import ru.otus.otuskotlin.cleaningaggregator.api.v1.apiV1ResponseSerialize
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.IRequest
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.IResponse
import ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers.fromTransport
import ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers.toTransport
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext

class ConsumerStrategyV1 : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: ClagContext): String {
        val response: IResponse = source.toTransport()
        return apiV1ResponseSerialize(response)
    }

    override fun deserialize(value: String, target: ClagContext) {
        val request: IRequest = apiV1RequestDeserialize(value)
        target.fromTransport(request)
    }
}
