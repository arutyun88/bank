package com.project.bankAccount.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class Reader {
    private final static String ENCODING = System.getProperty("console.encoding", "utf-8");
    private final String PATH;

    public Reader(@Value(value = "${path.files.resources}") String path) {
        this.PATH = path;
    }

    public List<String> reader(String fileName) {
        List<String> list = new ArrayList<>();
        try {
            Scanner input = new Scanner(Paths.get(PATH + fileName), ENCODING);
            while (input.hasNextLine()) {
                list.add(input.nextLine().trim());
            }
        } catch (IOException e) {
            System.out.println("Файл '" + fileName + "' не найден в директории '" + PATH + "'");
        }
        return list;
    }
}
