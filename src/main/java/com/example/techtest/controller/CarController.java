package com.example.techtest.controller;

import com.example.techtest.dto.CarDtoRequest;
import com.example.techtest.dto.CarDtoResponse;
import com.example.techtest.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import jakarta.validation.Valid;
import java.time.Year;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/cars")
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDtoResponse>> getAllCars(Pageable pageable) {
        List<CarDtoResponse> cars = carService.getAllCars(pageable);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDtoResponse> getCarById(@PathVariable Long id) {
        CarDtoResponse carDtoResponse = carService.getDtoById(id);
        return ResponseEntity.ok(carDtoResponse);
    }

    @GetMapping("/query")
    public ResponseEntity<List<CarDtoResponse>> getCarsByParams(
            @RequestParam(value = "vehicle", required = false) String vehicle,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "year", required = false) Integer year) {

        Year parsedYear = (year != null) ? Year.of(year) : null;
        List<CarDtoResponse> carDtoResponses = carService.getByParams(vehicle, brand, parsedYear);
        return ResponseEntity.ok(carDtoResponses);
    }

    @PostMapping
    public ResponseEntity<String> createCar(@RequestBody @Valid CarDtoRequest carDtoRequest) {
        carService.addCar(carDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Car created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDtoResponse> updateCar(@PathVariable Long id, @RequestBody CarDtoRequest carDtoRequest) {
        CarDtoResponse updatedCarDtoResponse = carService.updateCar(id, carDtoRequest);
        return ResponseEntity.ok(updatedCarDtoResponse);
    }

    @PutMapping("/setSold/{id}")
    public ResponseEntity<CarDtoResponse> isSold(@PathVariable Long id){
        CarDtoResponse car = carService.setSold(id);
        return ResponseEntity.ok(car);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable Long id) {
        String deleteMessage = carService.deleteById(id);
        return ResponseEntity.ok(deleteMessage);
    }
}
