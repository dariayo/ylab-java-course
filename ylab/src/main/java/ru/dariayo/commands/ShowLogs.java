package ru.dariayo.commands;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import ru.dariayo.Command;
import ru.dariayo.log.AuditLog;
import ru.dariayo.log.AuditLogRepository;
import ru.dariayo.repositories.PersonCollection;

public class ShowLogs extends Command {
    private final PersonCollection personCollection;
    private final AuditLogRepository auditLogRepository;

    public ShowLogs(PersonCollection personCollection, AuditLogRepository auditLogRepository) {
        this.personCollection = personCollection;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public void execute() {
        System.out.println("Введите параметр фильтрации: Add Person, Person login, Create order, Update order, Add car, Remove car");
        Scanner scanner = new Scanner(System.in);
        String param = scanner.nextLine();
        List<AuditLog> filteredLogs = auditLogRepository.filterLogs(null, LocalDateTime.now(), null, param);
        System.out.println("\nFiltered logs:");
        for (AuditLog log : filteredLogs) {
            System.out.println(log);
        }
    }

    @Override
    public String getName() {
        return "logs";
    }

}
