package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTests {

    @Test
    public void combineUsingMerge() {

        /*
         * The emission order is not synchronous in case of merge method.
         * Use case is we are fetching two flux of data from two different
         * table, and combining those data to send it. Here it is not sure
         * the A will be emitted first. It may be A or D, whichever gets the
         * chance. Emission order is not maintained.
         * */
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.merge(flux1, flux2);

        StepVerifier.create(mergedFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C", "D", "E", "F")
                    .verifyComplete();

    }

    @Test
    public void combineUsingMerge_WithDelay() {
        /*
         * While merging two flux the emitting of elements are not synchronous. In below case,
         * it is possible that first element may be A or D, not definite that it will be A only.
         * Also if A is emitted next element will be D as flux1 will wait for 1 second delay, until
         * then flux2 will start its emission.
         * */
        Flux<String> flux1 = Flux.just("A", "B", "C")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> mergedFlux = Flux.merge(flux1, flux2);
        StepVerifier.create(mergedFlux.log())
                    .expectSubscription()
                    .expectNextCount(6)
//                    .expectNext("A", "B", "C", "D", "E", "F")
                    .verifyComplete();


    }

    @Test
    public void combineUsingConcat() {
        /*
         * Combining fluxes with concat() method is a synchronous process.
         * Here data is emitted one after another in a sequential order.
         * */
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");
        Flux<String> concatFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C", "D", "E", "F")
                    .verifyComplete();
    }

    @Test
    public void combineUsingConcat_WithDelay() {
        /*
         * Here as concat is used to combine two flux, the order will be maintained.
         * Data will be emitted one after another in a sequential order. First flux1
         * will emits it data starting from A then delaying for 1 seconds and so on.
         * After that flux2 will emit its data starting from D.
         * */
        Flux<String> flux1 = Flux.just("A", "B", "C")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F")
                                 .delayElements(Duration.ofSeconds(1));

        Flux<String> concatFlux = Flux.concat(flux1, flux2);

        StepVerifier.create(concatFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C", "D", "E", "F")
                    .verifyComplete();
    }

    @Test
    public void combineUsingZip() {
        
        /*
        * When we want to combine two elements of flux into single element use zip() method.
        * Here first element of flux1 will be concat with first element of flux2, like
        * AD, BE, CF. Also the order is also maintained for emitting the data.
        * */
        Flux<String> flux1 = Flux.just("A", "B", "C")
                                 .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F")
                                 .delayElements(Duration.ofSeconds(1));
        
        
        Flux<String> zippedFlux = Flux.zip(flux1, flux2, (t1, t2) -> t1.concat(t2));

        StepVerifier.create(zippedFlux.log())
                    .expectSubscription()
                    .expectNext("AD", "BE", "CF")
                    .verifyComplete();
    }
}
