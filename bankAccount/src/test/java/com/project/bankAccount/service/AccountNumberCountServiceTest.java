package com.project.bankAccount.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountNumberCountServiceTest {

    @Test
    void accountNumberGenerate() {
        assertEquals(64580023710L, AccountNumberCountService.accountNumber(
                "Абрам",
                "Красинец",
                "Романович"
        ));
    }
}