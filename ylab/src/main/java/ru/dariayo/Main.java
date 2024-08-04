package ru.dariayo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import ru.dariayo.log.AuditLog;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.model.Car;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            AuditLogRepository auditLogRepository = new AuditLogRepository();
            PersonCollection personCollection = new PersonCollection(auditLogRepository);
            CarCollection carCollection = new CarCollection(auditLogRepository);
            OrderCollection orderCollection = new OrderCollection(auditLogRepository);
            CommandManager commandManager = new CommandManager(personCollection, carCollection, orderCollection,
                    auditLogRepository);
            System.out.println("Введите команду login для входа или register для регистрации");
            String input;
            Person person = new Person("input", "input", "user", "input");
            personCollection.addPerson(person);
            Person person1 = new Person("minput", "input", "user", "ainput");
            personCollection.addPerson(person1);
            Car car = new Car("input", "input", 2, 2, "s");
            carCollection.addCar(car);
            Car car1 = new Car("ainput", "ainput", 3, 4, "5");
            carCollection.addCar(car1);
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();
                commandManager.existCommand(input);

            } while (!input.equals("exit"));
            auditLogRepository.exportLogsToFile("audit_log.txt");
        }
    }
}