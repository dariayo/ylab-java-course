package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.dariayo.LiquibaseManager;

@RestController
@RequestMapping("/api")
public class CommandController {

    private final LiquibaseManager liquibaseManager;

    @Autowired
    public CommandController(LiquibaseManager liquibaseManager) {
        this.liquibaseManager = liquibaseManager;
    }

    /**
     * Create database with liquibase
     * 
     * @return
     */
    @PutMapping("/createBase")
    public ResponseEntity<String> createBase() {
        try {
            liquibaseManager.createBase();
            return ResponseEntity.status(HttpStatus.CREATED).body("Database created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create database: " + e.getMessage());
        }
    }

    @PostMapping("/command")
    public ResponseEntity<String> executeCommand(@RequestParam String command) {
        if (command == null || command.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Command cannot be null or empty");
        }

        String responseMessage;
        switch (command.toLowerCase()) {
            case "login":
                responseMessage = "Login command executed";
                break;
            case "register":
                responseMessage = "Register command executed";
                break;
            case "help":
                responseMessage = "Available commands: login, register, help";
                break;
            default:
                responseMessage = "Unknown command";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }

        return ResponseEntity.ok(responseMessage);
    }
}
