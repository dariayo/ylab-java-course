package ru.dariayo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import liquibase.exception.LiquibaseException;
import ru.dariayo.factory.CollectionFactory;
import ru.dariayo.factory.DefaultCollectionFactory;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            LiquibaseManager liquibaseManager = new LiquibaseManager();
            liquibaseManager.createBase();
            CollectionFactory factory = new DefaultCollectionFactory();

            CommandManager commandManager = factory.createCommandManager();
            System.out.println("Введите команду login для входа или register для регистрации, help - все команды");
            String input;
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();
                commandManager.existCommand(input);

            } while (!input.equals("exit"));
            // auditLogRepository.exportLogsToFile("audit_log.txt");
        } catch (SQLException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }
}