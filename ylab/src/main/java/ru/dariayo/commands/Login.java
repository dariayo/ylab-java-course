package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.PersonCollection;

public class Login extends Command{

    private final PersonCollection personCollection;

    public Login(PersonCollection personCollection) {
        this.personCollection = personCollection;

    }

    @Override
    public void execute() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите имя пользователя:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            personCollection.checkUser(username, password);
        }
    }

    @Override
    public String getName() {
        return "login";
    }

}
