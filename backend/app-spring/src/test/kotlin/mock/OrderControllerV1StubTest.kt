package ru.otus.otuskotlin.cleaningaggregator.app.spring.mock

import org.springframework.boot.test.mock.mockito.MockBean
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.cleaningaggregator.app.spring.config.OrderConfig
import ru.otus.otuskotlin.cleaningaggregator.app.spring.controllers.OrderControllerV1
import ru.otus.otuskotlin.cleaningaggregator.api.v1.models.*
import ru.otus.otuskotlin.cleaningaggregator.common.ClagContext
import ru.otus.otuskotlin.cleaningaggregator.api.v1.mappers.*
import ru.otus.otuskotlin.cleaningaggregator.biz.ClagOrderProcessor
import kotlin.test.Test

@WebFluxTest(OrderControllerV1::class, OrderConfig::class)
internal class OrderControllerV1StubTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: ClagOrderProcessor

    @Test
    fun createOrder() = testStubOrder(
        "/v1/order/create",
        OrderCreateRequest(),
        ClagContext().toTransportCreate()
    )

    @Test
    fun readOrder() = testStubOrder(
        "/v1/order/read",
        OrderReadRequest(),
        ClagContext().toTransportRead()
    )

    @Test
    fun updateOrder() = testStubOrder(
        "/v1/order/update",
        OrderUpdateRequest(),
        ClagContext().toTransportUpdate()
    )

    @Test
    fun deleteOrder() = testStubOrder(
        "/v1/order/delete",
        OrderDeleteRequest(),
        ClagContext().toTransportDelete()
    )

    @Test
    fun publishOrder() = testStubOrder(
        "/v1/order/publish",
        OrderPublishRequest(),
        ClagContext().toTransportPublish()
    )

    @Test
    fun respondOrder() = testStubOrder(
        "/v1/order/respond",
        OrderRespondRequest(),
        ClagContext().toTransportRespond()
    )

    @Test
    fun searchOrder() = testStubOrder(
        "/v1/order/search",
        OrderSearchRequest(),
        ClagContext().toTransportSearch()
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubOrder(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
