package com.project.bankAccount.controller;

import com.project.bankAccount.model.BankAccount;
import com.project.bankAccount.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    @RequestMapping
    public List<BankAccount> printAccounts() {
        return bankAccountService.create();
    }
}
