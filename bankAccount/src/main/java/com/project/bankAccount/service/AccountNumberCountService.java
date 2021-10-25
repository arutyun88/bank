package com.project.bankAccount.service;

public class AccountNumberCountService {
    private static long count = 64580000000L;

    public static long accountNumber(String firstName, String lastName, String patronymic) {
        long result = 0;
        result += accountNumberEncoding(firstName);
        result += accountNumberEncoding(lastName);
        result += accountNumberEncoding(patronymic);
        count += result;
        return count;
    }

    private static long accountNumberEncoding(String value) {
        long result = 0;
        char[] valueChar = value.toCharArray();
        for (char c : valueChar) {
            result += c;
        }
        return result;
    }

    public static void countClear() {
        count = 64580000000L;
    }
}
