package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ru.dariayo.dto.CarDTO;
import ru.dariayo.mapper.CarMapper;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarCollection carRepository;
    private final CarMapper carMapper = CarMapper.INSTANCE;

    @Autowired
    public CarController(CarCollection carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> getCars() {
        List<CarDTO> carDtos = carRepository.getCars().stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDtos);
    }

    @PostMapping
    public ResponseEntity<CarDTO> addCar(@Valid @RequestBody CarDTO carDTO) {
        Car car = carMapper.carDTOToCar(carDTO);
        carRepository.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
    }

    @PutMapping("/update/{mark}/{model}")
    public ResponseEntity<?> updateCar(
            @PathVariable String mark,
            @PathVariable String model,
            @Valid @RequestBody CarDTO updatedCarDTO) {
        if (mark == null || model == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        Car updatedCar = carMapper.carDTOToCar(updatedCarDTO);
        carRepository.updateCar(mark, model, updatedCar);
        return ResponseEntity.ok(updatedCarDTO);
    }

    @DeleteMapping("/remove/{mark}/{model}")
    public ResponseEntity<?> removeCar(@PathVariable String mark, @PathVariable String model) {
        if (mark == null || model == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        boolean result = carRepository.removeCar(mark, model);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCar(@RequestParam String param, @RequestParam String value) {
        if (param == null || value == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is missing");
        }
        List<CarDTO> carDtos = carRepository.searchCar(param, value).stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDtos);
    }
}
