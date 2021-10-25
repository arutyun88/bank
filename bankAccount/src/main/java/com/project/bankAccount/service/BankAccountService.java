package com.project.bankAccount.service;

import com.project.bankAccount.model.BankAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class BankAccountService {
    private final static List<String> MAN_FIRST_NAMES = new ArrayList<>();
    private final static List<String> MAN_LAST_NAMES = new ArrayList<>();
    private final static List<String> MAN_PATRONYMICS = new ArrayList<>();
    private final static List<String> WOMAN_FIRST_NAMES = new ArrayList<>();
    private final static List<String> WOMAN_LAST_NAMES = new ArrayList<>();
    private final static List<String> WOMAN_PATRONYMICS = new ArrayList<>();
    private final static List<BankAccount> ALL_BANK_ACCOUNTS = new ArrayList<>();
    private final static List<String> FILE_NAMES = new LinkedList<>();
    static {
        FILE_NAMES.add("MansNames.txt");
        FILE_NAMES.add("MansSurnames.txt");
        FILE_NAMES.add("MansPatronymics.txt");
        FILE_NAMES.add("WomansNames.txt");
        FILE_NAMES.add("WomansSurnames.txt");
        FILE_NAMES.add("WomansPatronymics.txt");
    }

    @Autowired
    private Reader reader;

    public List<BankAccount> create() {
        findAll();

        List<BankAccount> manBankAccounts = createList(
                MAN_FIRST_NAMES,
                MAN_LAST_NAMES,
                MAN_PATRONYMICS
        );
        List<BankAccount> womanBankAccounts = createList(
                WOMAN_FIRST_NAMES,
                WOMAN_LAST_NAMES,
                WOMAN_PATRONYMICS
        );

        if (ALL_BANK_ACCOUNTS.size() != 0) {
            for (BankAccount manBankAccount : manBankAccounts) {
                for (int j = 0; j < ALL_BANK_ACCOUNTS.size(); j++) {
                    if (ALL_BANK_ACCOUNTS.get(j).getAccountNumber() == manBankAccount.getAccountNumber()) {
                        break;
                    }
                    if (j == ALL_BANK_ACCOUNTS.size() - 1 &&
                            ALL_BANK_ACCOUNTS.get(j).getAccountNumber() != manBankAccount.getAccountNumber()) {
                        ALL_BANK_ACCOUNTS.add(manBankAccount);
                    }
                }
            }

            for (BankAccount womanBankAccount : womanBankAccounts) {
                for (int j = 0; j < ALL_BANK_ACCOUNTS.size(); j++) {
                    if (ALL_BANK_ACCOUNTS.get(j).getAccountNumber() == womanBankAccount.getAccountNumber()) {
                        break;
                    }
                    if (j == ALL_BANK_ACCOUNTS.size() - 1) {
                        ALL_BANK_ACCOUNTS.add(womanBankAccount);
                    }
                }
            }
        } else {
            ALL_BANK_ACCOUNTS.addAll(manBankAccounts);
            ALL_BANK_ACCOUNTS.addAll(womanBankAccounts);
        }

        clear();
        return ALL_BANK_ACCOUNTS;
    }

    private void findAll() {
        for (String fileName : FILE_NAMES) {
            switch (fileName) {
                case "MansNames.txt":
                    MAN_FIRST_NAMES.addAll(reader.reader(fileName));
                    break;
                case "MansSurnames.txt":
                    MAN_LAST_NAMES.addAll(reader.reader(fileName));
                    break;
                case "MansPatronymics.txt":
                    MAN_PATRONYMICS.addAll(reader.reader(fileName));
                    break;
                case "WomansNames.txt":
                    WOMAN_FIRST_NAMES.addAll(reader.reader(fileName));
                    break;
                case "WomansSurnames.txt":
                    WOMAN_LAST_NAMES.addAll(reader.reader(fileName));
                    break;
                case "WomansPatronymics.txt":
                    WOMAN_PATRONYMICS.addAll(reader.reader(fileName));
                    break;
            }
        }
    }

    private static List<BankAccount> createList(
            List<String> firstNames,
            List<String> lastNames,
            List<String> patronymics) {

        int minSize;
        if (firstNames.size() <= lastNames.size()
                && firstNames.size() <= patronymics.size()) {
            minSize = firstNames.size();
        } else if (lastNames.size() <= firstNames.size()
                && lastNames.size() <= patronymics.size()) {
            minSize = lastNames.size();
        } else {
            minSize = patronymics.size();
        }

        List<BankAccount> result = new ArrayList<>();
        for (int i = 0; i < minSize; i++) {
            result.add(new BankAccount(
                    firstNames.get(i),
                    lastNames.get(i),
                    patronymics.get(i),
                    AccountNumberCountService.accountNumber(
                            firstNames.get(i),
                            lastNames.get(i),
                            patronymics.get(i))
                    )
            );
        }
        return result;
    }

    private static void clear() {
        AccountNumberCountService.countClear();
        MAN_FIRST_NAMES.clear();
        MAN_LAST_NAMES.clear();
        MAN_PATRONYMICS.clear();
        WOMAN_FIRST_NAMES.clear();
        WOMAN_LAST_NAMES.clear();
        WOMAN_PATRONYMICS.clear();
    }
}
