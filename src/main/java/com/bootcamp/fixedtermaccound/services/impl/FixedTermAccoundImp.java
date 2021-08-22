package com.bootcamp.fixedtermaccound.services.impl;

import com.bootcamp.fixedtermaccound.models.dto.Customer;
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


/**
 * The type Fixed term accound imp.
 */
@Service
public class FixedTermAccoundImp implements IFixedTermAccoundService {

    /**
     * @param LOGGER para el manejo de trazas     *
     *
     */
    private static final Logger LOGGER  =   LoggerFactory.getLogger(FixedTermAccoundImp.class);

    /**
     * configurar dependendia repopsitory.
     */
    @Autowired
    private FixedTermAccountRepository repository;

    /**
     * configurar dependendia webClient.
     */
    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     *
     * @param fixedTeramAccound
     * @return
     */
    @Override
    public Mono<FixedTermAccound> create(final FixedTermAccound fixedTeramAccound) {
        return repository.save(fixedTeramAccound);
    }

    /**
     * @return
     */
    @Override
    public Flux<FixedTermAccound> findAll() {
        return repository.findAll();
    }

    /**
     * sobrescribir metodo findById para buscar por parametro.
     * @param id the id
     * @return
     */
    @Override
    public Mono<FixedTermAccound> findById(final String id) {
        return repository.findById(id);
    }

    /**
     * sobrescribir metodo update para actualizar.
     * @param fixedTermAccound
     * @return
     */
    @Override
    public Mono<FixedTermAccound> update(final FixedTermAccound fixedTermAccound) {
        return repository.save(fixedTermAccound);
    }

    /**
     * sobrescribir metodo delete para eliminar.
     * @param fixedTermAccound
     * @return
     */
    @Override
    public Mono<Void> delete(final FixedTermAccound fixedTermAccound) {
        return repository.delete(fixedTermAccound);
    }

    /**
     * sobrescribir el metodo getCustomer para consumir el servicio externo.
     * @param customerIdentityNumber the customer identity number
     * @return
     */
    @Override
    public Mono<Customer> getCustomer(final String customerIdentityNumber) {
        Map<String, Object> params = new HashMap<>();
        LOGGER.info("initializing client query");
        params.put("customerIdentityNumber", customerIdentityNumber);
        return webClientBuilder
            .baseUrl("http://CUSTOMER-SERVICE/customer")
            .build()
            .get()
            .uri("/findCustomerCredit/{customerIdentityNumber}", customerIdentityNumber)
            .accept(MediaType.APPLICATION_JSON)
            .exchangeToMono(clientResponse -> clientResponse.bodyToMono(Customer.class))
            .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }

    /**
     * sobrescribir el metodo validateCustomerIdentityNumber
     * para buscar si existe el customer por número de identidad.
     * @param customerIdentityNumber the customer identity number
     * @return
     */
    @Override
    public Mono<FixedTermAccound> validateCustomerIdentityNumber(final String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber)
                .switchIfEmpty(Mono.just(FixedTermAccound.builder()
                        .customerIdentityNumber(null).build()));
    }

    /**
     * sobrescribir el metodo findByCustomerIdentityNumber
     * para buscar por número de identidad.
     * @param customerIdentityNumber the customer identity number
     * @return
     */
    @Override
    public Mono<FixedTermAccound> findByCustomerIdentityNumber(final String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber);
    }

    /**
     * sobrescribir el metodo findByAccountNumber
     * para buscar por número de cuenta.
     * @param accountNumber the account number
     * @return
     */
    @Override
    public Mono<FixedTermAccound> findByAccountNumber(final String accountNumber) {
        LOGGER.info("El AccountNumber es" + accountNumber);
        return repository.findByAccountNumber(accountNumber);
    }

}
