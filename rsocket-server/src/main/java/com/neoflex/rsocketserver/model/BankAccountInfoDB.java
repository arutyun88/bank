package com.neoflex.rsocketserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("bankaccountinfo")
public class BankAccountInfoDB {

    @PrimaryKey("uuid")
    @Column("uuid")
    private UUID uuid;

    @Column("account")
    private BankAccount bankAccount;

    @Column("address")
    private Address address;
}
