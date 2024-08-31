package ru.dariayo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {
        Car car = carMapper.carDTOToCar(carDTO);
        carRepository.addCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carDTO);
    }

    @PutMapping("/update/{mark}/{model}")
    public ResponseEntity<CarDTO> updateCar(
            @PathVariable String mark,
            @PathVariable String model,
            @RequestBody CarDTO updatedCarDTO) {
        Car updatedCar = carMapper.carDTOToCar(updatedCarDTO);
        carRepository.updateCar(mark, model, updatedCar);
        return ResponseEntity.ok(updatedCarDTO);
    }

    @DeleteMapping("/remove/{mark}/{model}")
    public ResponseEntity<Void> removeCar(@PathVariable String mark, @PathVariable String model) {
        boolean result = carRepository.removeCar(mark, model);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CarDTO>> searchCar(@RequestParam String param, @RequestParam String value) {
        List<CarDTO> carDtos = carRepository.searchCar(param, value).stream()
                .map(carMapper::carToCarDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carDtos);
    }
}
