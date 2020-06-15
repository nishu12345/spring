package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoFilterTests {
    List<String> people = List.of("Adam", "Anna", "Maria", "Eve");

    @Test
    public void filterFluxTest() {
        Flux<String> stringFlux = Flux.fromIterable(people);
        Flux<String> startsWithA = stringFlux.filter(name -> name.startsWith("A"));

        StepVerifier.create(startsWithA.log())
                .expectNext("Adam", "Anna")
                .verifyComplete();
    }

    @Test
    public void filterFluxLengthTest() {
        Flux<String> nameGreaterThanFourCharacter = Flux.fromIterable(people)
                .filter(name -> name.length() > 4);
        
        StepVerifier.create(nameGreaterThanFourCharacter)
                .expectNext("Maria")
                .verifyComplete();
    }


}
