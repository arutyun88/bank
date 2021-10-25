package com.neoflex.grpcserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType
public class BankAccount {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Long accountNumber;
    private AccountType accountType;
}
