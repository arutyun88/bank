package com.neoflex.producer.service;

import com.neoflex.producer.model.AccountType;
import org.springframework.stereotype.Service;

@Service
public class AccountTypeService {

    public static AccountType getAccountType(String firstName, String lastName, String patronymic) {
        long countResultEncoding = encoding(firstName) + encoding(lastName) + encoding(patronymic);
        String[] type = String.valueOf(countResultEncoding).split("");
        int value = Integer.parseInt(type[type.length - 1]);
        return value == 0 ?
                AccountType.CURRENT_ACCOUNT : value == 1 ?
                AccountType.PAYMENT_ACCOUNT : value == 2 ?
                AccountType.PERSONAL_ACCOUNT : value == 3 ?
                AccountType.CARD_ACCOUNT : value == 4 ?
                AccountType.CARD_ACCOUNT : value == 5 || value == 6 ?
                AccountType.CARD_ACCOUNT : value == 7 || value == 8 ?
                AccountType.CARD_ACCOUNT :
                AccountType.SPECIAL_ACCOUNT;
    }

    private static long encoding(String value) {
        long result = 0;
        char[] valueChar = value.toCharArray();
        for (char c : valueChar) {
            result += c;
        }
        return result;
    }
}
