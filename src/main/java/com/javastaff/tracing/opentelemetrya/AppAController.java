package com.javastaff.tracing.opentelemetrya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static reactor.core.scheduler.Schedulers.boundedElastic;

@Component
public class AppAController {

    private Mono<String> sendTest() {
        return Mono.just("hello");
    }

    /**
     * Map API on processors.
     *
     * @return routing
     */
    public RouterFunction<ServerResponse> router() {
        return nest(path("/"), route()
                .GET("/test-tracing", testTracing())
                .build()
        );
    }

    private HandlerFunction<ServerResponse> testTracing() {
        return request -> sendTest()
                .map(s -> "Remote server said:" + s)
                .subscribeOn(boundedElastic())
                .transform(subscriptions -> ok().contentType(APPLICATION_JSON).body(subscriptions, String.class));
    }
}