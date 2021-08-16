package com.bootcamp.fixedtermaccound.services;

import com.bootcamp.fixedtermaccound.models.dto.CustomerDTO;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccound;
import reactor.core.publisher.Mono;

public interface IFixedTermAccoundService extends ICRUDService<FixedTermAccound,String> {

    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber);
    public Mono<FixedTermAccound> validateCustomerIdentityNumber(String customerIdentityNumber);
    public Mono<FixedTermAccound> findByCustomerIdentityNumber(String customerIdentityNumber);
    public Mono<FixedTermAccound> findByAccountNumber(String accountNumber);

}
