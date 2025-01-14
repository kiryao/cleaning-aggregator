package ru.otus.otuskotlin.cleaningaggregator.app.kafka

fun main() {
    val config = AppKafkaConfig()
    val consumer = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()))
    consumer.start()
}
