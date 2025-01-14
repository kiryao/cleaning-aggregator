package ru.otus.otuskotlin.cleaningaggregator.app.kafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import org.junit.Test
import ru.otus.otuskotlin.cleaningaggregator.api.v1.apiV1RequestSerialize
import ru.otus.otuskotlin.cleaningaggregator.api.v1.apiV1ResponseDeserialize
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import java.util.*
import kotlin.test.assertEquals

class KafkaControllerTest {
    @Test
    fun runKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer = consumer, producer = producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "test-1",
                    apiV1RequestSerialize(
                        OrderCreateRequest(
                            order = OrderCreateObject(
                                description = "Нужен качественный исполнитель на долгое время, буду делать повторные заказы",
                                cleaningType = CleaningType.MAINTENANCE,
                                sheduledFor = "2025-01-12T00:00:00Z",
                                specialRequirements = "Специальные требования к уборке",
                                address = OrderAddress(
                                    city = "Москва",
                                    street = "Строителей",
                                    house = "1",
                                    apartment = "1",
                                    floor = 1,
                                    entranceNumber = "1",
                                )
                            ),
                            debug = OrderDebug(mode = OrderRequestDebugMode.STUB, stub = OrderRequestDebugStubs.SUCCESS)
                        ),
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message = producer.history().first()
        val result = apiV1ResponseDeserialize<OrderCreateResponse>(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals("Нужен качественный исполнитель на долгое время, буду делать повторные заказы", result.order?.description)
    }

    companion object {
        const val PARTITION = 0
    }
}
