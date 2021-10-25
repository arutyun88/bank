package com.neoflex.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("bankAccountInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountInfo {
    @PrimaryKey
    private UUID uuid;
    private Account account;
    private Address address;
}
