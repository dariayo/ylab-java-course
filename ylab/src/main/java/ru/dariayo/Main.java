package ru.dariayo;

import java.io.IOException;
import java.util.Scanner;

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
            Person person = new Person("Mike", "1234", "user", "mike@gmail.com");
            personCollection.addPerson(person);
            Person person1 = new Person("Katya", "567", "user", "katya@yandex.ru");
            personCollection.addPerson(person1);
            Car car = new Car("Bmw", "M5", 2004, 4000, "Well");
            carCollection.addCar(car);
            Car car1 = new Car("Toyota", "Land", 1998, 2000, "Bad");
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