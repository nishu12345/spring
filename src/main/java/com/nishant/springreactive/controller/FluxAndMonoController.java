package com.nishant.springreactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

    /*Here we are sending the flux, but browser is doing the blocking way of communication
     * i.e it will display data once all data has been reached to it, similar as a typical
     * rest api without spring webflux.
     * */
    @GetMapping("/getIntegerFlux")
    public Flux<Integer> getIntegerFlux() {
        return Flux.range(1, 5)
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
        return Flux.range(1, 5)
                   .delayElements(Duration.ofSeconds(1))
                   .log();
    }
}
