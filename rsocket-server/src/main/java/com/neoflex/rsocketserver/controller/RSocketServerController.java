package com.neoflex.rsocketserver.controller;

import com.datastax.oss.driver.shaded.guava.common.base.Function;
import com.neoflex.rsocketserver.model.BankAccountInfo;
import com.neoflex.rsocketserver.model.BankAccountInfoDB;
import com.neoflex.rsocketserver.repository.BankAccountInfoRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Controller
public class RSocketServerController {
    @Autowired
    @Setter
    private BankAccountInfoRepository bankAccountInfoRepository;

    @MessageMapping("request-response")
    public Mono<BankAccountInfo> getByID(UUID uuid) {
        log.info("Method 'getByID' is used for '" + uuid + "'");
        return bankAccountInfoRepository.findById(uuid)
                .map((Function<BankAccountInfoDB, BankAccountInfo>) bankAccountInfoDB ->
                        new BankAccountInfo(
                                bankAccountInfoDB != null ? bankAccountInfoDB.getBankAccount() : null,
                                bankAccountInfoDB != null ? bankAccountInfoDB.getAddress() : null
                        )
                );
    }

    @MessageMapping("request-stream")
    public Flux<BankAccountInfo> getAll() {
        log.info("Method 'getAll' is used");
        return bankAccountInfoRepository.findAll()
                .map((Function<BankAccountInfoDB, BankAccountInfo>) bankAccountInfoDB ->
                        new BankAccountInfo(
                                bankAccountInfoDB != null ? bankAccountInfoDB.getBankAccount() : null,
                                bankAccountInfoDB != null ? bankAccountInfoDB.getAddress() : null
                        )
                );

    }

    @MessageMapping("fire-forget")
    public Mono<Void> deleteByID(UUID uuid) {
        log.info("Method 'deleteByID' is used for '" + uuid + "'");
        return bankAccountInfoRepository.deleteById(uuid);
    }
}
