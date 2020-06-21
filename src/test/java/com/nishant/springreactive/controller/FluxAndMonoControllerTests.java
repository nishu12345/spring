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
     * the total count of output using hasSize() method.
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

}
