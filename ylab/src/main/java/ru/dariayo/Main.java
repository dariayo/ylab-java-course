package ru.dariayo;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LiquibaseManager liquibaseManager = new LiquibaseManager();
            liquibaseManager.createBase();

            System.out.println("Введите команду login для входа или register для регистрации, help - все команды");
            String input;
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();

            } while (!input.equals("exit"));
            // auditLogRepository.exportLogsToFile("audit_log.txt");
        }
    }
}