package ru.dariayo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandManager commandManager = new CommandManager();
        String input;
        do {
            if (!scanner.hasNextLine())
                return;
            input = scanner.nextLine();

        } while (!input.equals("exit"));
    }
}