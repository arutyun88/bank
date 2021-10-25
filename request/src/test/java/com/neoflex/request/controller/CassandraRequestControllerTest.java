package com.neoflex.request.controller;

import com.neoflex.request.model.Address;
import com.neoflex.request.model.BankAccount;
import com.neoflex.request.model.BankAccountInfo;
import com.neoflex.request.repository.CassandraRequestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.BDDMockito.given;

class CassandraRequestControllerTest {

    @Mock
    CassandraRequestRepository repository;

    CassandraRequestController controller;

    public CassandraRequestControllerTest(){
        MockitoAnnotations.initMocks(this);
        controller = new CassandraRequestController();
        controller.setRequestRepository(repository);
    }

    @Test
    void findByIdTest() {
        BankAccount account = new BankAccount(
                UUID.randomUUID(),
                "Иван", "Иванов", "Иванович",
                645321987L, "CARD_ACCOUNT"
        );

        Address address = new Address("Московский проспект","город Воронеж","Воронежская область");

        BankAccountInfo bankAccountInfo = new BankAccountInfo(account.getUuid(), account, address);

        given(
                repository.findById(account.getUuid())
        ).willReturn(
            Optional.of(bankAccountInfo)
        );
        assertSame(
                account,
                controller
                        .findById(account.getUuid())
                        .get()
                        .getAccount()
        );
    }
}