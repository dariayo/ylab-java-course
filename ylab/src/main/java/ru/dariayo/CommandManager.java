package ru.dariayo;

import java.util.HashMap;

import ru.dariayo.commands.*;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.*;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class CommandManager {
    private static HashMap<String, Command> commands = new HashMap<>();

    public CommandManager(PersonCollection personCollection, CarCollection carCollection,
            OrderCollection orderCollection, AuditLogRepository auditLogRepository,
            ConsoleUserInterface userInterface) {
        commands = new HashMap<>();
        initializeCommand(new Register(personCollection, userInterface));
        initializeCommand(new Login(personCollection, userInterface));
        initializeCommand(new Show(carCollection));
        initializeCommand(new AddCar(carCollection, personCollection, userInterface));
        initializeCommand(new UpdateCar(carCollection, personCollection));
        initializeCommand(new RemoveCar(carCollection, personCollection));
        initializeCommand(new BuyCar(personCollection, carCollection, orderCollection, userInterface));
        initializeCommand(new ShowUsers(personCollection));
        initializeCommand(new Cancel(orderCollection, userInterface));
        initializeCommand(new ChangeOrder(orderCollection, userInterface));
        initializeCommand(new Search(orderCollection, userInterface));
        initializeCommand(new FilterUsers(personCollection, userInterface));
        initializeCommand(new SearchCar(carCollection, userInterface));
        initializeCommand(new ShowLogs(personCollection, auditLogRepository, userInterface));
        initializeCommand(new Help(userInterface));
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
