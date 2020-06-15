package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.function.Function;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTests {
    List<String> people = List.of("adam", "ana", "jack", "jenny");

    @Test
    public void transformUsingMap() {
        Flux<String> allCapsNames = Flux.fromIterable(people)
                                        .map(String::toUpperCase);

        StepVerifier.create(allCapsNames.log())
                    .expectNext("ADAM", "ANA", "JACK", "JENNY")
                    .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length() {
        Flux<Integer> nameLength = Flux.fromIterable(people)
                                       .map(String::length);

        StepVerifier.create(nameLength.log())
                    .expectNext(4, 3, 4, 5)
                    .verifyComplete();
    }

    @Test
    public void transformUsingMap_Length_Repeat() {
        Flux<Integer> nameLengthRepeat = Flux.fromIterable(people)
                                             .map(String::length)
                                             .repeat(1);

        StepVerifier.create(nameLengthRepeat)
                    .expectNext(4, 3, 4, 5, 4, 3, 4, 5)
                    .verifyComplete();

    }

    @Test
    public void transformUsingFilter_Map() {
        Flux<String> greaterThanFourAllCaps = Flux.fromIterable(people)
                                                  .filter(name -> name.length() > 4)
                                                  .map(String::toUpperCase);

        StepVerifier.create(greaterThanFourAllCaps)
                    .expectNext("JENNY")
                    .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F"))
                                      .flatMap(s -> Flux.fromIterable(convertToList1.apply(s)));


        StepVerifier.create(stringFlux.log())
                    .expectNextCount(12)
                    .verifyComplete();

    }

    private Function<String, List<String>> convertToList1 = s -> {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return List.of(s, "new Value");
    };

    @Test
    public void transformUsingFlatMap_UsingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F"))
                                      .window(2)
                                      .flatMap(s -> s.map(convertToList1::apply)
                                                     .subscribeOn(parallel()))
                                      .flatMap(s -> Flux.fromIterable(s));

        StepVerifier.create(stringFlux.log())
                    .expectNextCount(12)
                    .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_MaintainOrder() {
        Flux<String> stringFlux = Flux.fromIterable(List.of("A", "B", "C", "D", "E", "F"))
                                      .window(2)
                                      .flatMapSequential(s -> s.map(convertToList1::apply)
                                                               .subscribeOn(parallel()))
                                      .flatMap(s -> Flux.fromIterable(s));

        StepVerifier.create(stringFlux.log())
                    .expectNextCount(12)
                    .verifyComplete();
    }
}
