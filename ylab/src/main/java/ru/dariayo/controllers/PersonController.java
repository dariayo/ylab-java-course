package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ru.dariayo.dto.LoginRequestDTO;
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

    /**
     * Execute add person to db
     * 
     * @param personDTO DTO with person data
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<UserDTO> addPerson(@Valid @RequestBody UserDTO personDTO) {
        Person person = personMapper.personDTOToPerson(personDTO);
        personRepository.addPerson(person);
        UserDTO createdPersonDTO = personMapper.personToPersonDTO(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonDTO);
    }

    /**
     * Execute login user
     * 
     * @param loginRequest DTO with data for login
     * @return Response with 200 and message about successful login or 401
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank() ||
                loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password are required");
        }

        boolean success = personRepository.checkUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (success) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /**
     * Return list of all users
     * 
     * 
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = personRepository.getUsers().stream()
                .map(personMapper::personToPersonDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }
}
