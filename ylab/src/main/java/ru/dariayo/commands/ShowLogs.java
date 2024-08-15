package ru.dariayo.commands;

import java.time.LocalDateTime;
import java.util.List;

import ru.dariayo.Command;
import ru.dariayo.log.AuditLog;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.PersonCollection;
import ru.dariayo.userInterface.ConsoleUserInterface;

public class ShowLogs extends Command {
    private final PersonCollection personCollection;
    private final AuditLogRepository auditLogRepository;
    private final ConsoleUserInterface userInterface;

    public ShowLogs(PersonCollection personCollection, AuditLogRepository auditLogRepository, ConsoleUserInterface userInterface) {
        this.personCollection = personCollection;
        this.auditLogRepository = auditLogRepository;
        this.userInterface = userInterface;
    }

    @Override
    public void execute() {
        String param = userInterface.getInput(
                "Введите параметр фильтрации: Add Person, Person login, Create order, Update order, Add car, Remove car");
        List<AuditLog> filteredLogs = auditLogRepository.filterLogs(null, LocalDateTime.now(), null, param);
        userInterface.showMessage("\nFiltered logs:");
        for (AuditLog log : filteredLogs) {
            System.out.println(log);
        }
    }

    @Override
    public String getName() {
        return "logs";
    }

}
