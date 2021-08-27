package com.bootcamp.fixedtermaccound.config;


import com.bootcamp.fixedtermaccound.handler.FixedTermAccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * The type Router config.
 */
@Configuration
public class RouterConfig {

    /**
     * Routes router function.
     *
     * @param fixedTermAccountHandler the fixed term accound handler
     * @return the router function
     */
    @Bean
    public RouterFunction<ServerResponse> routes(FixedTermAccountHandler fixedTermAccountHandler){

        return route(GET("/api/fixedTermAccount"), fixedTermAccountHandler::findAll)
                .andRoute(GET("/api/fixedTermAccount/{customerIdentityNumber}"), fixedTermAccountHandler::findByCustomerIdentityNumber)
                .andRoute(GET("/api/fixedTermAccount/account/{accountNumber}"), fixedTermAccountHandler::findByAccountNumber)
                .andRoute(POST("/api/fixedTermAccount"), fixedTermAccountHandler::newFixedTermAccount)
                .andRoute(PUT("/api/fixedTermAccount/{id}"), fixedTermAccountHandler::updateFixedTermAccound)
                .andRoute(DELETE("/api/fixedTermAccount/{id}"), fixedTermAccountHandler::deleteFixedTermAccound);

    }

}
