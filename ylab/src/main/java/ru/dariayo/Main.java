package ru.dariayo;

import java.util.Scanner;

import ru.dariayo.model.Person;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PersonCollection personCollection = new PersonCollection();
            CarCollection carCollection = new CarCollection();
            OrderCollection orderCollection = new OrderCollection();
            CommandManager commandManager = new CommandManager(personCollection, carCollection, orderCollection);
            System.out.println("Введите команду login для входа или register для регистрации");
            String input;
            Person person = new Person("input", "input", "user", "input");
            personCollection.addPerson(person);
            Person person1 = new Person("minput", "input", "user", "ainput");
            personCollection.addPerson(person1);
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();
                commandManager.existCommand(input);

            } while (!input.equals("exit"));
        }
    }
}