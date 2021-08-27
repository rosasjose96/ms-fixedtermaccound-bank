package com.bootcamp.fixedtermaccound.services.impl;

import com.bootcamp.fixedtermaccound.models.dto.Customer;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccount;
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
    public Mono<FixedTermAccount> create(final FixedTermAccount fixedTeramAccound) {
        return repository.save(fixedTeramAccound);
    }

    /**
     * @return
     */
    @Override
    public Flux<FixedTermAccount> findAll() {
        return repository.findAll();
    }

    /**
     * sobrescribir metodo findById para buscar por parametro.
     * @param id the id
     * @return
     */
    @Override
    public Mono<FixedTermAccount> findById(final String id) {
        return repository.findById(id);
    }

    /**
     * sobrescribir metodo update para actualizar.
     * @param fixedTermAccount
     * @return
     */
    @Override
    public Mono<FixedTermAccount> update(final FixedTermAccount fixedTermAccount) {
        return repository.save(fixedTermAccount);
    }

    /**
     * sobrescribir metodo delete para eliminar.
     * @param fixedTermAccount
     * @return
     */
    @Override
    public Mono<Void> delete(final FixedTermAccount fixedTermAccount) {
        return repository.delete(fixedTermAccount);
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
    public Mono<FixedTermAccount> validateCustomerIdentityNumber(final String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber)
                .switchIfEmpty(Mono.just(FixedTermAccount.builder()
                        .customerIdentityNumber(null).build()));
    }

    /**
     * sobrescribir el metodo findByCustomerIdentityNumber
     * para buscar por número de identidad.
     * @param customerIdentityNumber the customer identity number
     * @return
     */
    @Override
    public Mono<FixedTermAccount> findByCustomerIdentityNumber(final String customerIdentityNumber) {
        return repository.findByCustomerIdentityNumber(customerIdentityNumber);
    }

    /**
     * sobrescribir el metodo findByAccountNumber
     * para buscar por número de cuenta.
     * @param accountNumber the account number
     * @return
     */
    @Override
    public Mono<FixedTermAccount> findByAccountNumber(final String accountNumber) {
        LOGGER.info("El AccountNumber es" + accountNumber);
        return repository.findByAccountNumber(accountNumber);
    }

}
