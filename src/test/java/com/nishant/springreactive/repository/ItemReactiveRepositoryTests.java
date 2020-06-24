package com.nishant.springreactive.repository;

import com.nishant.springreactive.document.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemReactiveRepositoryTests {

    @Autowired
    private ItemReactiveRepository itemReactiveRepository;

    @Test
    public void getItemByDescriptionTest() {
        StepVerifier.create(itemReactiveRepository.findByDescription("Google Home Mini"))
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
    }

    @Test
    public void saveItemTest() {
        Item item = new Item(null, "Google Home Mini", 30.0);
        Mono<Item> savedItem = itemReactiveRepository.save(item);
        StepVerifier.create(savedItem)
                    .expectSubscription()
                    .expectNextMatches(item1 -> item1.getId() != null && item1.getDescription()
                                                                              .equals("Google Home Mini"))
                    .verifyComplete();
    }

    @Test
    public void updateItemTest() {
        Flux<Item> updatedItem = itemReactiveRepository.findByDescription("Google Home Mini")
                                                       .map(item -> {
                                                           item.setPrice(520.0);
                                                           return item;
                                                       })
                                                       .flatMap(item -> itemReactiveRepository.save(item));

        StepVerifier.create(updatedItem)
                    .expectSubscription()
                    .expectNextMatches(item -> item.getPrice()
                                                   .equals(520.0))
                    .verifyComplete();
    }

    @Test
    public void deleteByIDTest() {
        Flux<Void> deletedItem = itemReactiveRepository.findByDescription("Google Home Mini")
                                                       .map(Item::getId)
                                                       .flatMap(itemID -> itemReactiveRepository.deleteById(itemID));
    }
}
