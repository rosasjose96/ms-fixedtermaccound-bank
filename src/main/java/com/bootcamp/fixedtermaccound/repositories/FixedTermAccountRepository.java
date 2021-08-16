package com.bootcamp.fixedtermaccound.repositories;

import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccound;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface FixedTermAccountRepository extends ReactiveMongoRepository<FixedTermAccound,String> {

    public Mono<FixedTermAccound> findByCustomerIdentityNumber(String customerIdentityNumber);
    public Mono<FixedTermAccound> findByAccountNumber(String accountNumber);

}
