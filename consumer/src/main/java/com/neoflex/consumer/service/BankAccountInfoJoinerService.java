package com.neoflex.consumer.service;

import com.neoflex.consumer.model.Account;
import com.neoflex.consumer.model.Address;
import com.neoflex.consumer.model.AccountWithAddress;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class BankAccountInfoJoinerService implements ValueJoiner<Account, Address, AccountWithAddress> {
    @Override
    public AccountWithAddress apply(Account account, Address address) {
        return new AccountWithAddress(account, address);
    }
}
