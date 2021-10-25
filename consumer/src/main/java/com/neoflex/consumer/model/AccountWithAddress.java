package com.neoflex.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountWithAddress {
    private Account account;
    private Address address;
}
