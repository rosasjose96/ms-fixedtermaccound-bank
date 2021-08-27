package com.bootcamp.fixedtermaccound.repositories;

import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * The interface Fixed term account repository.
 */
public interface FixedTermAccountRepository extends ReactiveMongoRepository<FixedTermAccount,String> {

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
