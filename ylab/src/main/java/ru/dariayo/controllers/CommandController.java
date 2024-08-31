package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping("/createBase")
    public String createBase() {
        liquibaseManager.createBase();
        return "Database created successfully";
    }

    @GetMapping("/command")
    public String executeCommand(@RequestParam String command) {
        switch (command.toLowerCase()) {
            case "login":
                return "Login command executed";
            case "register":
                return "Register command executed";
            case "help":
                return "Available commands: login, register, help";
            default:
                return "Unknown command";
        }
    }
}
