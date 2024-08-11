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

public class DefaultCollectionFactory implements CollectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "123";

    @Override
    public PersonCollection createPersonCollection() throws SQLException {
        return new PersonCollection(createAuditLogRepository(), createDbManager());
    }

    @Override
    public DBManager createDbManager() throws SQLException {
        DBManager dbManager;

        dbManager = new DBManager(URL, USER_NAME, PASSWORD);
        dbManager.connectDB();
        dbManager.createTablePerson();
        System.out.println("sss");
        return dbManager;

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
    public CommandManager createCommandManager() throws SQLException {
        return new CommandManager(createPersonCollection(), createCarCollection(), createOrderCollection(),
                createAuditLogRepository(), createUserInterface());
    }

    @Override
    public ConsoleUserInterface createUserInterface() {
        return new ConsoleUserInterface();
    }
}
