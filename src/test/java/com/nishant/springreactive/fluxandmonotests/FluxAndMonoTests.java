package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTests {

    @Test
    public void fluxTest() {

        /*
         * stringFlux is the publisher with three data.
         * Then a RuntimeException is attached with it.
         * Also another publisher is attached after it.
         * */
        Flux<String> stringFlux = Flux
                .just("Spring", "Spring Boot", "ReactiveSpring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("Another Publisher"))
                .log();

        /*
         * Subscriber is calling subscribe method on Publisher(stringflux) to make subscription.
         * It has overloaded method, in which firstParameter will handle onNext(Item item) event.
         * Second parameter is handling onError(Throwable t) event.
         * Third parameter is handling onComplete() event. It will be executed when data transmission
         * has been completed without any error.
         * */
        stringFlux.subscribe(System.out::println,
                e -> System.err.println(e),
                () -> System.out.println("Completed"));

    }

    @Test
    public void fluxTest_WithoutError() {
        Flux<String> stringPublisher = Flux
                .just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringPublisher)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();
    }

    @Test
    public void fluxTest_WithError() {
        Flux<String> stringPublisher = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringPublisher)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTest_WithError1() {
        Flux<String> stringPublisher = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringPublisher)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError(RuntimeException.class)
//                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithoutError() {
        Flux<String> stringPublisher = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringPublisher)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void fluxTestElementsCount_WithError() {
        Flux<String> stringPublisher = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringPublisher)
                .expectNextCount(3)
                .expectError(RuntimeException.class)
//                .expectErrorMessage("Exception Occurred")
                .verify();
    }


    @Test
    public void monoTest() {
        Mono<String> monoPublisher = Mono.just("Spring");

        StepVerifier.create(monoPublisher.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoErrorTest() {
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
