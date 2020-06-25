package com.nishant.springreactive.intialize;

import com.nishant.springreactive.document.Item;
import com.nishant.springreactive.repository.ItemReactiveRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Profile("!test")
public class ItemDataInitializer implements CommandLineRunner {

    private final ItemReactiveRepository itemReactiveRepository;

    public ItemDataInitializer(ItemReactiveRepository itemReactiveRepository) {
        this.itemReactiveRepository = itemReactiveRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        initialSetup();
    }

    private void initialSetup() {
        itemReactiveRepository.deleteAll()
                              .thenMany(getNewItems())
                              .flatMap(itemReactiveRepository::save)
                              .thenMany(itemReactiveRepository.findAll())
                              .subscribe(item -> System.out.println("Item Inserted from CommandLine Runner : " + item));
    }

    private Flux<Item> getNewItems() {
        return Flux.just(new Item(null, "Samsung TV", 399.99),
                new Item(null, "LG TV", 329.99),
                new Item(null, "Apple Watch", 349.99),
                new Item("beatsID", "Beats HeadPhones", 149.99));
    }
}
