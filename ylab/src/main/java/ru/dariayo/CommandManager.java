package ru.dariayo;

import java.util.HashMap;

import ru.dariayo.commands.Login;
import ru.dariayo.commands.Register;

public class CommandManager {
    private final PersonCollection personCollection;
    private static HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(PersonCollection personCollection) {
        this.personCollection = personCollection;
        commands = new HashMap<>();
        initializeCommand(new Register(personCollection));
        initializeCommand(new Login(personCollection));
    }

    public void existCommand(String command) {
        if (commands.containsKey(command)) {
            commands.get(command).execute();
        } else {
            System.out.println("Команды " + command + " не существует");
        }
    }

    void initializeCommand(Command command) {
        if (commands.containsKey(command.getName())) {
            throw new IllegalArgumentException("Данная команда уже есть");
        }
        commands.put(command.getName(), command);
    }

}
