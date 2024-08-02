package ru.dariayo;

import java.util.HashMap;

import ru.dariayo.commands.AddCar;
import ru.dariayo.commands.Login;
import ru.dariayo.commands.Register;
import ru.dariayo.commands.RemoveCar;
import ru.dariayo.commands.Show;
import ru.dariayo.commands.UpdateCar;

public class CommandManager {
    private static HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(PersonCollection personCollection, CarCollection carCollection) {
        commands = new HashMap<>();
        initializeCommand(new Register(personCollection));
        initializeCommand(new Login(personCollection));
        initializeCommand(new Show(carCollection));
        initializeCommand(new AddCar(carCollection, personCollection));
        initializeCommand(new UpdateCar(carCollection, personCollection));
        initializeCommand(new RemoveCar(carCollection, personCollection));
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
