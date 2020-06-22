package com.nishant.springreactive.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebFluxTest
public class FluxAndMonoControllerTests {

    @Autowired
    WebTestClient webTestClient;

    /*
     * This is the first approach to call api using WebTestClient.
     * Here we are getting response in form of Flux<T> as we are using
     * returnResult(T.class) method and from it fetching response body by
     * getResponseBody() method.
     * */
    @Test
    public void flux_approach1() {
        Flux<Integer> integerFlux = webTestClient.get()
                                                 .uri("/getIntegerFlux")
                                                 .accept(MediaType.APPLICATION_JSON)
                                                 .exchange()
                                                 .expectStatus()
                                                 .isOk()
                                                 .returnResult(Integer.class)
                                                 .getResponseBody();

        StepVerifier.create(integerFlux)
                    .expectSubscription()
                    .expectNext(1, 2, 3, 4)
                    .verifyComplete();
    }

    /*
     * Second approach to call api using WebTestClient. Here we are validating
     * the total count of output using hasSize() method. As we have used expectBodyList(T.class)
     * we will not get Flux, but it will wait for all the data to be emitted and then convert it
     * to List.
     * */
    @Test
    public void flux_approach2() {
        webTestClient.get()
                     .uri("/getIntegerFlux")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectHeader()
                     .contentType(MediaType.APPLICATION_JSON)
                     .expectBodyList(Integer.class)
                     .hasSize(4);
    }

    /*
     * Third approach to call api using WebTestClient.
     * Here we can convert flux to list. expectBodyList() will wait
     * until receiving all the data and then convert it into List<Integer>.
     * expectBodyList(T.class) is used to get response in List<T> type.
     * To do so we need to call returnResult().getResponseBody().
     * */

    @Test
    public void flux_approach3() {
        List<Integer> expectedResult = List.of(1, 2, 3, 4);
        EntityExchangeResult<List<Integer>> listEntityExchangeResult = webTestClient.get()
                                                                                    .uri("/getIntegerFlux")
                                                                                    .accept(MediaType.APPLICATION_JSON)
                                                                                    .exchange()
                                                                                    .expectStatus()
                                                                                    .isOk()
                                                                                    .expectBodyList(Integer.class)
                                                                                    .returnResult();
        assertEquals(expectedResult, listEntityExchangeResult.getResponseBody());
    }

    /*
     * As we have used expectBody(T.class) method it is going to give response
     * as List, so our consumeWith() method's assertEqual is successfully passed.
     * */
    @Test
    public void flux_approach4() {
        List<Integer> expectedList = List.of(1, 2, 3, 4);
        webTestClient.get()
                     .uri("/getIntegerFlux")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBodyList(Integer.class)
                     .consumeWith(result -> assertEquals(expectedList, result.getResponseBody()));
    }

    /*
     * This is the approach to test infinte flux. We need to cancel() the flux
     * and then verify it.
     * */
    @Test
    public void infiniteFlux_Test() {
        Flux<Long> infiniteFlux = webTestClient.get()
                                               .uri("/getInfiniteFlux")
                                               .accept(MediaType.APPLICATION_STREAM_JSON)
                                               .exchange()
                                               .expectStatus()
                                               .isOk()
                                               .returnResult(Long.class)
                                               .getResponseBody();

        StepVerifier.create(infiniteFlux)
                    .expectSubscription()
                    .expectNext(0L, 1L, 2L)
                    .thenCancel()
                    .verify();
    }

    /*
     * Test case to test api which return mono. Here as we have used expectBody() method it will
     * change Mono to Integer as we call getResponseBody() on it.
     * */
    @Test
    public void integerMono_Test() {
        webTestClient.get()
                     .uri("/getIntegerMono")
                     .accept(MediaType.APPLICATION_STREAM_JSON)
                     .exchange()
                     .expectStatus()
                     .isOk()
                     .expectBody(Integer.class)
                     .consumeWith(response -> assertEquals(1, response.getResponseBody()));
    }

}
