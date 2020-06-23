package com.nishant.springreactive.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class SampleHandlerFunctionTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testFluxHandler() {
        Flux<Integer> integerFlux = webTestClient.get()
                                                 .uri("/functional/flux")
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

    @Test
    public void testMonoHandler() {
        webTestClient.get()
                     .uri("/functional/mono")
                     .accept(MediaType.APPLICATION_JSON)
                     .exchange()
                     .expectBody(Integer.class)
                     .consumeWith(result -> assertEquals(1, result.getResponseBody()));
    }
}
