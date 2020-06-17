package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTests {

    @Test
    public void fluxErrorHandling_OnErrorResume() {
        /*
         * Here onErrorResume() method is handling the exception thrown by string flux. So
         * in the test case we are expecting "A" "B" "C" and flux is returned by onErrorResume()
         * method having data as "Default" and "Default1". You always have to return a publisher
         * from the onErrorResume() method, like a flux or a mono. Nothing else can be returned.
         * Like we can't return a string.
         * */
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                                      .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                                      .concatWith(Flux.just("D"))
                                      .onErrorResume(e -> {
                                          System.out.println("Exception is : " + e);
                                          return Flux.just("Default", "Default1");
                                      });

        StepVerifier.create(stringFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C")
                    .expectNext("Default", "Default1")
                    .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorReturn() {
        /*
         * Here when exception is thrown from stringFlux, it is handled by onErrorReturn() method.
         * So after emitting "A", "B", "C", "Default" will be returned to subscriber. Also we have
         * to provide a fallback value from onErrorReturn() method. In this case "Default" is the
         * fallback value.
         * */
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                                      .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                                      .concatWith(Flux.just("D"))
                                      .onErrorReturn("Default");

        StepVerifier.create(stringFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C")
                    .expectNext("Default")
                    .verifyComplete();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap() {

        /*
         * onErrorMap() method handles the exception thrown from Publisher i.e
         * Flux in this case and mapped it to another exception. Use case will
         * be any java exception is thrown with some operation and we need to handle
         * it and throw our custom exception.
         * */
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                                      .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                                      .concatWith(Flux.just("D"))
                                      .onErrorMap(e -> new CustomException(e));


        StepVerifier.create(stringFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C")
                    .expectError(CustomException.class)
                    .verify();
    }

    @Test
    public void fluxErrorHandling_OnErrorMap_withRetry() {
        /*
         * Here stringflux will start emitting data and when exception is thrown,
         * it will retry emitting data twice because of retry(2) and after that exception
         * will be thrown. Use case can be if any error come from database call, retry
         * for n number of times before finally throwing an exception.
         * */
        Flux<String> stringFlux = Flux.just("A", "B", "C")
                                      .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                                      .concatWith(Flux.just("D"))
                                      .onErrorMap(e -> new CustomException(e))
                                      .retry(2);

        StepVerifier.create(stringFlux.log())
                    .expectSubscription()
                    .expectNext("A", "B", "C")
                    .expectNext("A", "B", "C")
                    .expectNext("A", "B", "C")
                    .expectError(RuntimeException.class)
                    .verify();
    }
    
}
