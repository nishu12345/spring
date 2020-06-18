package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTests {

    @Test
    public void testWithoutVirtualTime() {
        Flux<Long> finiteFlux = Flux.interval(Duration.ofSeconds(1))
                                    .take(3);

        StepVerifier.create(finiteFlux.log())
                    .expectSubscription()
                    .expectNext(0l, 1l, 2l)
                    .verifyComplete();
    }

    @Test
    public void testingWithVirtualTime() {

        /*
         * For testing person we can configure virtual time scheduler,
         * which will skip the waiting time of all the threads. We need
         * to provide flux as a supplier to withVirtualTime() method.
         * Also we need to call thenAwait(), and specifies for how much
         * duration virtual time will be considered.
         * */
        VirtualTimeScheduler.getOrSet();
        Flux<Long> finiteFlux = Flux.interval(Duration.ofSeconds(1))
                                    .take(3);

        StepVerifier.withVirtualTime(() -> finiteFlux)
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(3))
                    .expectNext(0l, 1l, 2l)
                    .verifyComplete();
    }
}
