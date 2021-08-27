package com.bootcamp.fixedtermaccound.services;

import com.bootcamp.fixedtermaccound.models.dto.Customer;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccount;
import reactor.core.publisher.Mono;

/**
 * The interface Fixed term accound service.
 */
public interface IFixedTermAccoundService extends ICRUDService<FixedTermAccount,String> {

    /**
     * Gets customer.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the customer
     */
    public Mono<Customer> getCustomer(String customerIdentityNumber);

    /**
     * Validate customer identity number mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    public Mono<FixedTermAccount> validateCustomerIdentityNumber(String customerIdentityNumber);

    /**
     * Find by customer identity number mono.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the mono
     */
    public Mono<FixedTermAccount> findByCustomerIdentityNumber(String customerIdentityNumber);

    /**
     * Find by account number mono.
     *
     * @param accountNumber the account number
     * @return the mono
     */
    public Mono<FixedTermAccount> findByAccountNumber(String accountNumber);

}
