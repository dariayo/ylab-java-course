package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.Person;
import ru.dariayo.PersonCollection;

public class Register extends Command {
    private final PersonCollection personCollection;

    public Register(PersonCollection personCollection) {
        this.personCollection = personCollection;

    }

    Scanner scanner = new Scanner(System.in);
    String pass = "123";

    @Override
    public void execute() {
        String username = getUsername();
        String password = getPassword();
        String role = getRole();
        String contacts = getContacts();
        Person person = new Person(username, password, role, contacts);
        personCollection.addPerson(person);

    }

    public String getUsername() {
        System.out.println("Введите имя пользователя:");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.println("Введите пароль:");
        return scanner.nextLine();
    }

    public String getRole() {
        System.out.println("Выберите роль: admin, manager, user");
        String role = scanner.nextLine();
        if (role.equals("admin") || role.equals("manager")) {
            System.out.println("Введите пароль:");
            if (scanner.nextLine() == pass) {
                return role;
            }
        }
        return role;
    }

    public String getContacts() {
        System.out.println("Введите почту:");
        return scanner.nextLine();
    }

    @Override
    public String getName() {
        return "register";
    }

}
