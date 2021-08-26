package com.bootcamp.fixedtermaccound.services;

import com.bootcamp.fixedtermaccound.models.dto.Credit;
import com.bootcamp.fixedtermaccound.models.dto.Creditcard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICreditService {
    Mono<Boolean> validateDebtorCredit(String customerIdentityNumber);

    Flux<Credit> getCredit(String customerIdentityNumber);

    Mono<Creditcard> getCreditcard(String customerIdentityNumber);
}
