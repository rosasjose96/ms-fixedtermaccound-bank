package com.bootcamp.fixedtermaccound.services.impl;

import com.bootcamp.fixedtermaccound.models.dto.CustomerDTO;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccound;
import com.bootcamp.fixedtermaccound.repositories.FixedTermAccountRepository;
import com.bootcamp.fixedtermaccound.services.IFixedTermAccoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Service
public class FixedTermAccoundImp implements IFixedTermAccoundService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedTermAccoundImp.class);

    @Autowired
    private FixedTermAccountRepository repository;

    @Autowired
    private WebClient client;

    @Override
    public Mono<FixedTermAccound> create(FixedTermAccound fixedTeramAccound) {
        return repository.save(fixedTeramAccound);
    }

    @Override
    public Flux<FixedTermAccound> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<FixedTermAccound> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<FixedTermAccound> update(FixedTermAccound fixedTermAccound) {
        return repository.save(fixedTermAccound);
    }

    @Override
    public Mono<Void> delete(FixedTermAccound fixedTermAccound) {
        return repository.delete(fixedTermAccound);
    }

    @Override
    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<String,Object>();
        LOGGER.info("initializing client query");
        params.put("customerIdentityNumber",customerIdentityNumber);
        return client.get()
                .uri("/findCustomerCredit/{customerIdentityNumber}",customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CustomerDTO.class))
                .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }

    @Override
    public Mono<FixedTermAccound> validateCustomerIdentityNumber(String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber)
                .switchIfEmpty(Mono.just(FixedTermAccound.builder()
                        .customerIdentityNumber(null).build()));
    }

    @Override
    public Mono<FixedTermAccound> findByCustomerIdentityNumber(String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber);
    }

    @Override
    public Mono<FixedTermAccound> findByAccountNumber(String accountNumber) {
        LOGGER.info("El AccountNumber es" + accountNumber);
        return repository.findByAccountNumber(accountNumber);
    }

}
