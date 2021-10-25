package com.neoflex.rsocketclient.controller;

import com.neoflex.rsocketclient.model.BankAccountInfo;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping
public class RSocketClientController {
    private final RSocketRequester REQUESTER;

    @Autowired
    public RSocketClientController(RSocketRequester requester) {
        this.REQUESTER = requester;
    }

    @GetMapping("/findAll")
    public Flux<BankAccountInfo> getAll(){
        return this.REQUESTER
                .route("request-stream")
                .retrieveFlux(BankAccountInfo.class);
    }

    @GetMapping(path = "/findById/{uuid}")
    public Mono<BankAccountInfo> getByID(@PathVariable("uuid") UUID uuid){
        return this.REQUESTER
                .route("request-response")
                .data(uuid)
                .retrieveMono(BankAccountInfo.class);
    }

    @GetMapping(path = "/deleteById/{uuid}")
    public Mono<Void> deleteByID(@PathVariable("uuid") UUID uuid){
        return this.REQUESTER.route("fire-forget").data(uuid).send();
    }
}
