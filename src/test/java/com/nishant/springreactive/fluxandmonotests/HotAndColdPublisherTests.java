package com.nishant.springreactive.fluxandmonotests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdPublisherTests {

    @Test
    public void coldPublisher() throws InterruptedException {
        /*
         * While delaying elements the emission of data to both subscriber is
         * happening in parallel i.e by two different threads. The amount of time
         * the main thread is sleeping we are viewing the data after that it is emitted
         * but can't be displayed on console.
         *
         * Also if we remove delayElements() method, all the processing will be done
         * by main thread only. First subscriber will print all the elements and then main
         * thread will sleep for 4 seconds and then another subscriber will start emitting
         * data.
         *
         * Cold Publisher are those publisher in which every subscriber emits data from
         * start only.
         * */

        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                                      .delayElements(Duration.ofSeconds(1))
                                      .log();

        stringFlux.subscribe(s -> System.out.println("Subscriber 1: " + s)); // emits data from beginning.
        Thread.sleep(4000);
        stringFlux.subscribe(s -> System.out.println("Subscriber 2: " + s)); // emits data from beginning.
        Thread.sleep(4000);
    }

    @Test
    public void hotPublisher() throws InterruptedException {
        /*
         * In hotPublisher the second subscriber is not emitting data from the beginning.
         * It will emit data where subscriber one has come, from there only data is emitted.
         * */
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E")
                                      .delayElements(Duration.ofSeconds(1))
                                      .log();

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();

        connectableFlux.subscribe(element -> System.out.println("Subscriber 1 : " + element));

        Thread.sleep(3000);
        connectableFlux.subscribe(element -> System.out.println("Subscriber 2 : " + element));
        Thread.sleep(3000);
    }


}
