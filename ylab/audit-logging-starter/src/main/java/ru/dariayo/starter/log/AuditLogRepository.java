package ru.dariayo.log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;

public class AuditLogRepository {
    private List<AuditLog> auditLogs = new ArrayList<>();

    public void logAction(String user, String action, String details) {
        auditLogs.add(new AuditLog(LocalDateTime.now(), user, action, details));
    }

    public List<AuditLog> getLogs() {
        return new ArrayList<>(auditLogs);
    }

    /**
     * filter logs
     * 
     * @param from
     * @param to
     * @param user
     * @param action
     * @return
     */
    public List<AuditLog> filterLogs(LocalDateTime from, LocalDateTime to, String user, String action) {
        return auditLogs.stream()
                .filter(log -> (from == null || !log.getTimestamp().isBefore(from)) &&
                        (to == null || !log.getTimestamp().isAfter(to)) &&
                        (user == null || log.getUser().equals(user)) &&
                        (action == null || log.getAction().equals(action)))
                .collect(Collectors.toList());
    }

    /**
     * export logs to file
     * 
     * @param filename
     * @throws IOException
     */
    public void exportLogsToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (AuditLog log : auditLogs) {
                writer.write(log.toString() + "\n");
            }
        }
    }
}