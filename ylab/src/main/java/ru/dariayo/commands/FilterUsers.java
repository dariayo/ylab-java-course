package ru.dariayo.commands;

import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

public class FilterUsers extends Command {
    private final PersonCollection personCollection;

    public FilterUsers(PersonCollection personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    public void execute() {
        if (!personCollection.getPerson().getRole().equals("user")) {
            System.out.println("Введите по какому параметру вы хотите сортировать: name, contacts, orders");
            Scanner scanner = new Scanner(System.in);
            String param = scanner.nextLine();
            personCollection.printSortedUsers(param);
        }
    }

    @Override
    public String getName() {
        return "filter_users";
    }

}
