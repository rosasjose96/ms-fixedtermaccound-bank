package com.bootcamp.fixedtermaccound.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * The type Web client config.
 */
@Configuration
public class WebClientConfig {


    /**
     * Register web client web client . builder.
     *
     * @return the web client . builder
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder registerWebClient() {
        return WebClient.builder();
    }

}
