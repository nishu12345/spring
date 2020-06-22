package com.nishant.springreactive.router;

import com.nishant.springreactive.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfig {

    /*Router Function which takes request and pass it to Handler Config.
     * Here the endpoints are mapped with respective methods.
     * */
    @Bean
    public RouterFunction<ServerResponse> routeEndpoints(SampleHandlerFunction sampleHandlerFunction) {
        return RouterFunctions.route(GET("/functional/flux").and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::getFlux)
                              .andRoute(GET("/functional/mono").and(accept(MediaType.APPLICATION_JSON)), sampleHandlerFunction::getMono);
    }
}
