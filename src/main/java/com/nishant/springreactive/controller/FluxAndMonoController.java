package com.nishant.springreactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
public class FluxAndMonoController {


    /*Here we are sending the flux, but browser is doing the blocking way of communication
     * i.e it will display data once all data has been reached to it, similar as a typical
     * rest api without spring webflux.
     * */
    @GetMapping("/getIntegerFlux")
    public Flux<Integer> getIntegerFlux() {
        return Flux.range(1, 4)
                   .delayElements(Duration.ofSeconds(1))
                   .log();
    }

    /*Here we are telling the rest api to produce data in a streaming format. So
     * browser will start receiving data as soon as it is emitted. The call is not blocking
     * i.e it will not wait for the whole data to come an then show it, rather it will start
     * showing data as soon as it comes.
     * */
    @GetMapping(value = "/getIntegerFluxAsStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getIntegerFluxAsStream() {
        return Flux.range(1, 4)
                   .delayElements(Duration.ofSeconds(1))
                   .log();
    }

    @GetMapping(value = "/getIntegerListASFlux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getIntegerList() {
        return Flux.fromIterable(List.of(1, 2, 3, 4))
                   .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/getInfiniteFlux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getInfiniteFlux() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/getIntegerMono", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Integer> getIntegerMono() {
        return Mono.just(1);
    }


}
