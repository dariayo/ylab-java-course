package ru.dariayo;

import java.io.IOException;
import java.util.Scanner;

import ru.dariayo.config.AppConfig;
import ru.dariayo.factory.CollectionFactory;
import ru.dariayo.factory.DefaultCollectionFactory;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            CollectionFactory factory = new DefaultCollectionFactory();
            AppConfig appConfig = new AppConfig(factory);

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
        }
    }
}