package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTests {

    @Test
    public void infiniteSequence() throws InterruptedException {
        /*
         * infiniteFlux is an infinite flux emitting elements from 0 to infinite.
         * The emission is happening at different thread, and when we are making
         * the main thread to sleep, we can see the value.
         * */
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200));

        infiniteFlux.log()
                    .subscribe(System.out::println);

        Thread.sleep(3000);
    }

    @Test
    public void finiteFlux_WithMap() {
        /*
         * Here finiteFlux is converting long value from infinite flux and
         * converting from Long to Integer. It will only take 3 value i.e
         * 0, 1, 2.
         * */
        Flux<Integer> finiteIntegerFlux = Flux.interval(Duration.ofSeconds(1))
                                              .map(n -> n.intValue())
                                              .take(3);


        StepVerifier.create(finiteIntegerFlux.log())
                    .expectSubscription()
                    .expectNext(0, 1, 2)
                    .verifyComplete();


    }

    @Test
    public void finiteFluxUsingMap_WithDelay() {

        /*
         * Here 3 elements will be emitted from infinteFlux and converted to integer.
         * It will be emitted after fixed interval of time. interval(Duration) or
         * delayElements(Duration) either will be used which ever is having greater value,
         * else for same value only one time will be considered.
         * */
        Flux<Integer> finiteIntegerFlux = Flux.interval(Duration.ofSeconds(1))
                                              .delayElements(Duration.ofSeconds(5))
                                              .map(n -> n.intValue())
                                              .take(3);

        StepVerifier.create(finiteIntegerFlux.log())
                    .expectSubscription()
                    .expectNext(0, 1, 2)
                    .verifyComplete();
    }

}
