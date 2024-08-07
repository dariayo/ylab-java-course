package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class Login extends Command {

    private final PersonCollection personCollection;
    private final ConsoleUserInterface userInterface;

    public Login(PersonCollection personCollection, ConsoleUserInterface userInterface) {
        this.personCollection = personCollection;
        this.userInterface = userInterface;

    }

    @Override
    public void execute() {
        String username = userInterface.getInput("Введите имя пользователя:");
        String password = userInterface.getInput("Введите пароль:");
        personCollection.checkUser(username, password);
    }

    @Override
    public String getName() {
        return "login";
    }

}
