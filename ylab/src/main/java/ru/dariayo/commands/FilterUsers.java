package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class FilterUsers extends Command {
    private final PersonCollection personCollection;
    private final ConsoleUserInterface userInterface;

    public FilterUsers(PersonCollection personCollection, ConsoleUserInterface userInterface) {
        this.personCollection = personCollection;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        if (!personCollection.getPerson().getRole().equals("user")) {
            String param = userInterface
                    .getInput("Введите по какому параметру вы хотите сортировать: name, contacts, orders");
            personCollection.printSortedUsers(param);
        }
    }

    @Override
    public String getName() {
        return "filter_users";
    }

}
