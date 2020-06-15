package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.stream.Stream;

public class FluxAndMonoFactoryTests {


    @Test
    public void fluxFromIterableTest() {
        List<String> namesList = List.of("Adam", "Eve", "Maria", "Bob");
        Flux<String> namesPublisher = Flux.fromIterable(namesList);

        StepVerifier.create(namesPublisher.log())
                .expectNext("Adam", "Eve", "Maria", "Bob")
                .verifyComplete();
    }


    @Test
    public void fluxFromArray() {
        String namesArray[] = {"Adam", "Eve", "Maria", "Bob"};
        Flux<String> namesPublisher = Flux.fromArray(namesArray);

        StepVerifier.create(namesPublisher.log())
                .expectNext("Adam", "Eve", "Maria", "Bob")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream() {
        Stream<String> sportStream = Stream.of("Cricket", "Football", "Tennis", "BasketBall");
        Flux<String> sportFlux = Flux.fromStream(sportStream);

        StepVerifier.create(sportFlux.log())
                .expectNext("Cricket")
                .expectNext("Football")
                .expectNext("Tennis")
                .expectNext("BasketBall")
                .verifyComplete();
    }
    
    
    @Test
    public void monoUsingJustOrEmpty(){
        Mono<String> emptyMono = Mono.justOrEmpty(null);
        StepVerifier.create(emptyMono.log())
                .verifyComplete();
    }
    
    @Test
    public void monoUsingSupplier(){
        Mono<String> stringMono = Mono.fromSupplier(() -> "Adam");
        
        StepVerifier.create(stringMono.log())
                .expectNext("Adam")
                .verifyComplete();
    }
    
    @Test
    public void fluxUsingRange(){
        Flux<Integer> integerFlux = Flux.range(1, 5);
        StepVerifier.create(integerFlux.log())
                .expectNext(1,2,3,4,5)
                .verifyComplete();
    }
}
