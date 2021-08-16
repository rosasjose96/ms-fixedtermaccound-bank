package com.bootcamp.fixedtermaccound.config;


import com.bootcamp.fixedtermaccound.handler.FixedTermAccoundHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(FixedTermAccoundHandler fixedTermAccoundHandler){

        return route(GET("/api/fixedTermAccound"), fixedTermAccoundHandler::findAll)
                .andRoute(GET("/api/fixedTermAccound/{customerIdentityNumber}"),fixedTermAccoundHandler::findByCustomerIdentityNumber)
                .andRoute(GET("/api/fixedTermAccound/account/{accountNumber}"), fixedTermAccoundHandler::findByAccountNumber)
                .andRoute(POST("/api/fixedTermAccound"), fixedTermAccoundHandler::newFixedTermAccound)
                .andRoute(PUT("/api/fixedTermAccound/{id}"), fixedTermAccoundHandler::updateFixedTermAccound)
                .andRoute(DELETE("/api/fixedTermAccound/{id}"), fixedTermAccoundHandler::deleteFixedTermAccound);

    }

}
