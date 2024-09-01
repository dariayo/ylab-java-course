package ru.dariayo.log;

import java.time.LocalDateTime;

public class AuditLog {
    private LocalDateTime timestamp;
    private String user;
    private String action;
    private String details;

    public AuditLog(LocalDateTime timestamp, String user, String action, String details) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return timestamp + " - " + user + " - " + action + " - " + details;
    }
}