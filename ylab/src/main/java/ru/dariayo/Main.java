package ru.dariayo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            PersonCollection collection = new PersonCollection();
            CommandManager commandManager = new CommandManager(collection);
            System.out.println("Введите команду login для входа или register для регистрации");
            String input;
            do {
                if (!scanner.hasNextLine())
                    return;
                input = scanner.nextLine();
                commandManager.existCommand(input);

            } while (!input.equals("exit"));
        }
    }
}