package ru.dariayo.factory;

import java.sql.SQLException;

import ru.dariayo.CommandManager;
import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;
import ru.dariayo.userInterface.UserInterface;

public interface CollectionFactory {
    PersonCollection createPersonCollection() throws SQLException;
    CarCollection createCarCollection() throws SQLException;
    OrderCollection createOrderCollection() throws SQLException;
    AuditLogRepository createAuditLogRepository();
    CommandManager createCommandManager() throws SQLException;
    ConsoleUserInterface createUserInterface();
    DBManager createDbManager() throws SQLException;
}
