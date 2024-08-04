package ru.dariayo.commands;

import ru.dariayo.Command;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

public class ShowUsers extends Command {
    private final PersonCollection personCollection;

    public ShowUsers(PersonCollection personCollection) {
        this.personCollection = personCollection;
    }

    @Override
    public void execute() {
        if (!personCollection.getPerson().getRole().equals("user")) {
            personCollection.info();
        }
    }

    @Override
    public String getName() {
        return "show_users";
    }

}
