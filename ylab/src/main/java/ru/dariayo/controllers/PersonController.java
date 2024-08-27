package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonCollection personRepository;

    @Autowired
    public PersonController(PersonCollection personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        personRepository.addPerson(person);
        return ResponseEntity.ok("Person added successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean success = personRepository.checkUser(username, password);
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping
    public ResponseEntity<List<Person>> getUsers() {
        List<Person> users = personRepository.getUsers();
        return ResponseEntity.ok(users);
    }
}