package ru.otus.otuskotlin.cleaningaggregator.app.kafka

import ru.otus.otuskotlin.cleaningaggregator.app.common.IClagAppSettings
import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import ru.otus.otuskotlin.cleaningaggregator.common.ClagCorSettings
import ru.otus.otuskotlin.cleaningaggregator.logging.common.LoggerProvider
import ru.otus.otuskotlin.cleaningaggregator.logging.jvm.loggerLogback

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInV1: String = KAFKA_TOPIC_IN_V1,
    val kafkaTopicOutV1: String = KAFKA_TOPIC_OUT_V1,
    override val corSettings: ClagCorSettings = ClagCorSettings(
        loggerProvider = LoggerProvider { loggerLogback(it) }
    ),
    override val processor: ClagOrderProcessor = ClagOrderProcessor(corSettings),
): IClagAppSettings {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"
        const val KAFKA_TOPIC_IN_V1_VAR = "KAFKA_TOPIC_IN_V1"
        const val KAFKA_TOPIC_OUT_V1_VAR = "KAFKA_TOPIC_OUT_V1"

        val KAFKA_HOSTS by lazy { (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,; ]\\s*") }
        val KAFKA_GROUP_ID by lazy { System.getenv(KAFKA_GROUP_ID_VAR) ?: "cleaningaggregator" }
        val KAFKA_TOPIC_IN_V1 by lazy { System.getenv(KAFKA_TOPIC_IN_V1_VAR) ?: "cleaningaggregator-order-v1-in" }
        val KAFKA_TOPIC_OUT_V1 by lazy { System.getenv(KAFKA_TOPIC_OUT_V1_VAR) ?: "cleaningaggregator-order-v1-out" }
    }
}
