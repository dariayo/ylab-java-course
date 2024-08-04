package ru.dariayo;

import java.util.HashMap;

import ru.dariayo.commands.*;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.*;

public class CommandManager {
    private static HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(PersonCollection personCollection, CarCollection carCollection,
            OrderCollection orderCollection, AuditLogRepository auditLogRepository) {
        commands = new HashMap<>();
        initializeCommand(new Register(personCollection));
        initializeCommand(new Login(personCollection));
        initializeCommand(new Show(carCollection));
        initializeCommand(new AddCar(carCollection, personCollection));
        initializeCommand(new UpdateCar(carCollection, personCollection));
        initializeCommand(new RemoveCar(carCollection, personCollection));
        initializeCommand(new Buy(personCollection, carCollection, orderCollection));
        initializeCommand(new ShowUsers(personCollection));
        initializeCommand(new Cancel(orderCollection));
        initializeCommand(new ChangeOrder(orderCollection));
        initializeCommand(new Search(orderCollection));
        initializeCommand(new FilterUsers(personCollection));
        initializeCommand(new SearchCar(carCollection));
        initializeCommand(new ShowLogs(personCollection, auditLogRepository));
        initializeCommand(new Help());
    }

    /**
     * check command when user input
     * 
     * @param command
     */
    public void existCommand(String command) {
        if (commands.containsKey(command)) {
            commands.get(command).execute();
        } else {
            System.out.println("Команды " + command + " не существует");
        }
    }

    /**
     * add command to command manager
     * 
     * @param command
     */
    void initializeCommand(Command command) {
        if (commands.containsKey(command.getName())) {
            throw new IllegalArgumentException("Данная команда уже есть");
        }
        commands.put(command.getName(), command);
    }

}
