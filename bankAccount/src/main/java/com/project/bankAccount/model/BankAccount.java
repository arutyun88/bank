package com.project.bankAccount.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class BankAccount {
    private final UUID uuid;
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final long accountNumber;

    public BankAccount(String firstName, String lastName, String patronymic, long accountNumber) {
        this.uuid = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.accountNumber = accountNumber;
    }
}
