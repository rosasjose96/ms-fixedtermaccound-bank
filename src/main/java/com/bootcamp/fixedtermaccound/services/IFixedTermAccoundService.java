package com.bootcamp.fixedtermaccound.services;

import com.bootcamp.fixedtermaccound.models.dto.CustomerDTO;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccound;
import reactor.core.publisher.Mono;

/**
 * The interface Fixed term accound service.
 */
public interface IFixedTermAccoundService extends ICRUDService<FixedTermAccound,String> {

    /**
     * Gets customer.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the customer
     */
    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber);

    /**
     * Validate customer identity number mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    public Mono<FixedTermAccound> validateCustomerIdentityNumber(String customerIdentityNumber);

    /**
     * Find by customer identity number mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    public Mono<FixedTermAccound> findByCustomerIdentityNumber(String customerIdentityNumber);

    /**
     * Find by account number mono.
     *
     * @param accountNumber the account number
     * @return the mono
     */
    public Mono<FixedTermAccound> findByAccountNumber(String accountNumber);

}
