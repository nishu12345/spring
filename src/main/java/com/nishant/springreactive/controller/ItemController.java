package com.nishant.springreactive.controller;

import com.nishant.springreactive.document.Item;
import com.nishant.springreactive.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nishant.springreactive.constants.GlobalConstants.V1_GET_ALL_ITEMS;
import static com.nishant.springreactive.constants.GlobalConstants.V1_SAVE_ITEM;
import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE;

@RestController
@Slf4j
public class ItemController {

    private final ItemReactiveRepository itemReactiveRepository;

    public ItemController(ItemReactiveRepository itemReactiveRepository) {
        this.itemReactiveRepository = itemReactiveRepository;
    }

    @GetMapping(value = V1_GET_ALL_ITEMS, produces = APPLICATION_STREAM_JSON_VALUE)
    public Flux<Item> findAllItems() {
        return itemReactiveRepository.findAll();
    }

    @PostMapping(value = V1_SAVE_ITEM, produces = APPLICATION_STREAM_JSON_VALUE)
    public Mono<Item> saveItem(@RequestBody Item item) {
        return itemReactiveRepository.save(item);
    }
}
