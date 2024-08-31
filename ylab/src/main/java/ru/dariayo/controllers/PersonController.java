package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ru.dariayo.dto.UserDTO;
import ru.dariayo.mapper.UserMapper;
import ru.dariayo.model.Person;
import ru.dariayo.repositories.PersonCollection;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonCollection personRepository;
    private final UserMapper personMapper = UserMapper.INSTANCE;

    @Autowired
    public PersonController(PersonCollection personRepository) {
        this.personRepository = personRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addPerson(@Valid @RequestBody UserDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        personRepository.addPerson(person);
        UserDTO createdPersonDTO = personMapper.personToPersonDTO(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean success = personRepository.checkUser(username, password);
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = personRepository.getUsers().stream()
                .map(personMapper::personToPersonDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
