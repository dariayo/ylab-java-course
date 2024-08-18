package ru.dariayo.factory;

import java.sql.SQLException;

import ru.dariayo.db.DBManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.*;

public interface CollectionFactory {
    PersonCollection createPersonCollection() throws SQLException;
    CarCollection createCarCollection() throws SQLException;
    OrderCollection createOrderCollection() throws SQLException;
    AuditLogRepository createAuditLogRepository();
    DBManager createDbManager() throws SQLException;
}
