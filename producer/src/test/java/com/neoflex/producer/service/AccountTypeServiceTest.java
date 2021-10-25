package com.neoflex.producer.service;

import com.neoflex.producer.model.AccountType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AccountTypeServiceTest {

    @Test
    void getAccountType() {
        Assertions.assertEquals(AccountType.PERSONAL_ACCOUNT, AccountTypeService.getAccountType(
                "Иван",
                "Иванов",
                "Иванович"
        ));
    }
}