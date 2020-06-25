package com.nishant.springreactive.controller;

import com.nishant.springreactive.constants.GlobalConstants;
import com.nishant.springreactive.document.Item;
import com.nishant.springreactive.repository.ItemReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
@ActiveProfiles("test")
public class ItemControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ItemReactiveRepository itemReactiveRepository;

    @BeforeEach
    public void setup() {
        /*Here blockLast() method make sure that all the data is inserted before every test case*/
        itemReactiveRepository.deleteAll()
                              .thenMany(getItemFlux())
                              .flatMap(itemReactiveRepository::save)
                              .doOnNext(item -> System.out.println("Inserted Item : " + item))
                              .blockLast();
    }

    private Flux<Item> getItemFlux() {
        return Flux.just(new Item(null, "Samsung TV", 399.99),
                new Item(null, "LG TV", 349.99),
                new Item(null, "Apple Watch", 299.99),
                new Item(null, "Beats HeadPhones", 199.99));
    }

    @Test
    public void findAllAPITests() {
        Flux<Item> itemFlux = webTestClient.get()
                                           .uri(GlobalConstants.V1_GET_ALL_ITEMS)
                                           .accept(MediaType.APPLICATION_STREAM_JSON)
                                           .exchange()
                                           .expectStatus()
                                           .isOk()
                                           .returnResult(Item.class)
                                           .getResponseBody();

        StepVerifier.create(itemFlux)
                    .expectSubscription()
                    .expectNextCount(4)
                    .verifyComplete();


    }
}
