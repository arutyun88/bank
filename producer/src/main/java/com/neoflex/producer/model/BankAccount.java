package com.neoflex.producer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoflex.producer.service.AccountTypeService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class BankAccount {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String patronymic;
    private long accountNumber;
    private AccountType accountType;

    public BankAccount(UUID uuid, String firstName, String lastName, String patronymic,
                       long accountNumber) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.accountNumber = accountNumber;
        this.accountType = AccountTypeService.getAccountType(firstName, lastName, patronymic);
    }
}
