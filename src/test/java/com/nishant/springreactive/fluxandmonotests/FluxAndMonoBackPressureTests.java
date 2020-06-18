package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTests {

    @Test
    public void backPressureTest() {

        /*
         * There is a flux from which will emit data from 1 to 10.
         * But after getting subscription we are request for single data
         * using thenRequest(1) and it will emmit only one data i.e 1.
         * After that we again request for single data using thenRequest(1)
         * and it will emmit next data i.e 2. And after that we are calling
         * cancel() on the subscription.
         * */
        Flux<Integer> oneToTen = Flux.range(1, 10);

        StepVerifier.create(oneToTen.log())
                    .expectSubscription()
                    .thenRequest(1)
                    .expectNext(1)
                    .thenRequest(1)
                    .expectNext(2)
                    .thenCancel()
                    .verify();


    }

    @Test
    public void backPressure() {

        /*
         * Here we are accessing only two elements after subscripting to the flux.
         * subscribe() method have four arguments.
         * first argument -> stream of data,
         * second argument -> executed in case of error thrown from flux,
         * third argument -> executed when onComplete() method is called,
         * fourth argument -> get the subscription object on which we are requesting
         *                      only 2 data by subscription.request(2).
         *
         * Now we can see "Done" is not printed,as flux after emitting 2 element
         * is being cancelled so onComplete() method is never executed.
         *
         * */
        Flux<Integer> oneToTen = Flux.range(1, 10);

        oneToTen.subscribe(element -> System.out.println("Element is : " + element),
                e -> System.err.println("Exception is : " + e),
                () -> System.out.println("Done"),
                subscription -> subscription.request(2));
    }

    @Test
    public void backPressure_cancel() {

        /*
         * Here nothing will be emitted, because we are subscribing to the publisher i.e flux.
         * But when flux is giving the subscription object to subscriber, it is calling cancel().
         * */
        Flux<Integer> oneToTen = Flux.range(1, 10);
        oneToTen.subscribe(element -> System.out.println("Element is : " + element),
                e -> System.err.println("Exception is : " + e),
                () -> System.out.println("Done"),
                subscription -> subscription.cancel());
    }

    @Test
    public void customized_backPressure() {

        /*
         * Here we are gaining more control over flux. After subscribing
         * we are requesting for single data and if data is equal to 4, it
         * will be cancelled. Likewise we can override hookOnCancel() and
         * do things when cancel() method is executed
         * onNext() method is emitting one element at a time, also request(1)
         * is asking for single element. So when onNext(1) have item 1, we are requesting 
         * for single data and hence "Element is 1" is printed.
         * Again onNext(2) have 2 as a value, and we are requesting for 1 data so
         * "Elements is 2" is printed
         * */
        Flux<Integer> oneToTen = Flux.range(1, 10);

        oneToTen.log()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        super.hookOnNext(value);
//                        request(2);
                        System.out.println("Element is " + value);
                        if (value == 4) {
                            cancel();
                        }
                    }
                });
    }
}
