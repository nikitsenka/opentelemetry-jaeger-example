package com.javastaff.tracing.opentelemetrya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * This is a global service entry point router.
 */
@Configuration
public class Router {

    @Autowired
    private AppAController controller;


    /**
     * Enables routes.
     *
     * @return functional routes
     */
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return controller.router();
    }

    /**
     * Configure web client bean.
     *
     * @return webClient
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }
}
