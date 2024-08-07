package ru.dariayo.factory;

import ru.dariayo.CommandManager;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.CarCollection;
import ru.dariayo.repositories.OrderCollection;
import ru.dariayo.repositories.PersonCollection;

public interface CollectionFactory {
    PersonCollection createPersonCollection();
    CarCollection createCarCollection();
    OrderCollection createOrderCollection();
    AuditLogRepository createAuditLogRepository();
    CommandManager createCommandManager();
}
