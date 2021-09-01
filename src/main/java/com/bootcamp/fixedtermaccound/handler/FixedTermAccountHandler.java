package com.bootcamp.fixedtermaccound.handler;


import com.bootcamp.fixedtermaccound.models.dto.CustomerDTO;
import com.bootcamp.fixedtermaccound.models.entities.FixedTermAccount;
import com.bootcamp.fixedtermaccound.services.ICreditService;
import com.bootcamp.fixedtermaccound.services.IFixedTermAccoundService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * The type Fixed term accound handler.
 */
@Slf4j(topic = "fixedTermAccound_handler")
@Component
public class FixedTermAccountHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixedTermAccountHandler.class);

    @Autowired
    private IFixedTermAccoundService service;

    @Autowired
    private ICreditService creditService;
    /**
     * Find all mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), FixedTermAccount.class);
    }

    /**
     * New fixed term accound mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> newFixedTermAccount(ServerRequest request) {
        Mono<FixedTermAccount> fixedTermAccoundMono = request.bodyToMono(FixedTermAccount.class);

        return fixedTermAccoundMono.flatMap(fixedTermAccount -> service.getCustomer(fixedTermAccount.getCustomerIdentityNumber())
                .filter(customer -> customer.getCustomerType().getCode().equals("1001")||customer.getCustomerType().getCode().equals("1002"))
                .flatMap(customerDTO -> {
                    fixedTermAccount.setTypeOfAccount("FIXEDTERM_ACCOUNT");
                    fixedTermAccount.setCustomer(CustomerDTO.builder()
                            .name(customerDTO.getName()).code(customerDTO.getCustomerType().getCode())
                            .customerIdentityNumber(customerDTO.getCustomerIdentityNumber()).build());
                    fixedTermAccount.setMaxLimitMovementPerMonth(fixedTermAccount.getMaxLimitMovementPerMonth());
                    fixedTermAccount.setMovementPerMonth(0);
                    return creditService.validateDebtorCredit(fixedTermAccount.getCustomerIdentityNumber())
                            .flatMap(debtor -> {
                                if(debtor == true) {
                                    return Mono.empty();
                                }else return service.validateCustomerIdentityNumber(fixedTermAccount.getCustomerIdentityNumber());
                            })
                            .flatMap(accountFound -> {
                                if(accountFound.getCustomerIdentityNumber() != null){
                                    LOGGER.info("La cuenta encontrada es: " + accountFound.getCustomerIdentityNumber());
                                    return Mono.empty();
                                }else {
                                    LOGGER.info("No se encontró la cuenta ");
                                    return service.create(fixedTermAccount);
                                }
                            });
                }))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c))
                ).switchIfEmpty(ServerResponse.badRequest().build());
    }

    /**
     * Update fixed term accound mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> updateFixedTermAccound(ServerRequest request) {
        Mono<FixedTermAccount> fixedTermAccountMono = request.bodyToMono(FixedTermAccount.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(fixedTermAccountMono, (db,req) -> {
            db.setAmount(req.getAmount());
            db.setMovementPerMonth(req.getMovementPerMonth());
            return db;
        }).flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.update(c), FixedTermAccount.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Delete fixed term accound mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> deleteFixedTermAccound(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<FixedTermAccount> creditMono = service.findById(id);

        return creditMono
                .doOnNext(c -> LOGGER.info("deleteConsumption: consumptionId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Find by customer identity number mono.
     *
     * @param serverRequest the server request
     * @return the mono
     */
    public Mono<ServerResponse> findByCustomerIdentityNumber(ServerRequest serverRequest) {
        String customerIdentityNumber =  serverRequest.pathVariable("customerIdentityNumber");
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findByCustomerIdentityNumber(customerIdentityNumber), FixedTermAccount.class);
    }

    /**
     * Find by account number mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<ServerResponse> findByAccountNumber(ServerRequest request) {
        String accountNumber = request.pathVariable("accountNumber");
        LOGGER.info("El AccountNumber es " + accountNumber);
        return service.findByAccountNumber(accountNumber).flatMap(c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
