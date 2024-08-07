package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class Register extends Command {
    private final PersonCollection personCollection;
    private final ConsoleUserInterface userInterface;

    public Register(PersonCollection personCollection, ConsoleUserInterface userInterface) {
        this.personCollection = personCollection;
        this.userInterface = userInterface;

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
        return userInterface.getInput("Введите имя пользователя:");
    }

    public String getPassword() {
        return userInterface.getInput("Введите пароль:");
    }

    public String getRole() {
        String role = userInterface.getInput("Выберите роль: admin, manager, user");
        if (role.equals("admin") || role.equals("manager")) {
            if (userInterface.getInput("Введите пароль:") == pass) {
                return role;
            }
        }
        return role;
    }

    public String getContacts() {
        return userInterface.getInput("Введите почту:");
    }

    @Override
    public String getName() {
        return "register";
    }

}
