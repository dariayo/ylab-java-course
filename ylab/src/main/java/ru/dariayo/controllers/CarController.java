package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dariayo.model.Car;
import ru.dariayo.repositories.CarCollection;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarCollection carRepository;

    @Autowired
    public CarController(CarCollection carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    public List<Car> getCars() {
        return carRepository.getCars();
    }

    @PostMapping
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        carRepository.addCar(car);
        return ResponseEntity.ok("Car added successfully");
    }

    @PutMapping("/update/{mark}/{model}")
    public ResponseEntity<String> updateCar(
            @PathVariable String mark,
            @PathVariable String model,
            @RequestBody Car updatedCar) {
        carRepository.updateCar(mark, model, updatedCar);
        return ResponseEntity.ok("Car updated successfully");
    }

    @DeleteMapping("/remove/{mark}/{model}")
    public ResponseEntity<String> removeCar(@PathVariable String mark, @PathVariable String model) {
        boolean result = carRepository.removeCar(mark, model);
        if (result) {
            return ResponseEntity.ok("Car removed successfully");
        } else {
            return ResponseEntity.status(404).body("Car not found");
        }
    }

    @GetMapping("/search")
    public List<Car> searchCar(@RequestParam String param, @RequestParam String value) {
        return carRepository.searchCar(param, value);
    }
}
