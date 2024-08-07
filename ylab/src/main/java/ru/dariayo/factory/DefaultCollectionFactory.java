package ru.dariayo.factory;

import ru.dariayo.CommandManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;
import ru.dariayo.userInterface.UserInterface;

public class DefaultCollectionFactory implements CollectionFactory {
    @Override
    public PersonCollection createPersonCollection() {
        return new PersonCollection(createAuditLogRepository());
    }

    @Override
    public CarCollection createCarCollection() {
        return new CarCollection(createAuditLogRepository());
    }

    @Override
    public OrderCollection createOrderCollection() {
        return new OrderCollection(createAuditLogRepository());
    }

    @Override
    public AuditLogRepository createAuditLogRepository() {
        return new AuditLogRepository();
    }

    @Override
    public CommandManager createCommandManager() {
        return new CommandManager(createPersonCollection(), createCarCollection(), createOrderCollection(),
                createAuditLogRepository(), createUserInterface());
    }

    @Override
    public ConsoleUserInterface createUserInterface() {
       return new ConsoleUserInterface();
    }
}
