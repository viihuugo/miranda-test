package com.example.techtest.service;

import com.example.techtest.dto.CarDtoRequest;
import com.example.techtest.dto.CarDtoRequestMapper;
import com.example.techtest.dto.CarDtoResponse;
import com.example.techtest.dto.CarDtoResponseMapper;
import com.example.techtest.exceptions.ChassiDuplicatedException;
import com.example.techtest.models.CarEntity;
import com.example.techtest.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
@Service
@AllArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final CarDtoResponseMapper carDtoResponseMapper;
    private final CarDtoRequestMapper carDtoRequestMapper;

    public List<CarDtoResponse> getAllCars(Pageable pageable) {
        Pageable pageableWithTenItems = PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        Page<CarEntity> carPage = carRepository.findAll(pageableWithTenItems);
        List<CarEntity> cars = carPage.getContent();

        return cars.stream()
            .map(carDtoResponseMapper)
            .collect(Collectors.toList());
    }

    public void addCar(@Valid CarDtoRequest carDtoRequest) {
        try {
            CarEntity carEntity = carDtoRequestMapper.toEntity(carDtoRequest);
            carRepository.save(carEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new ChassiDuplicatedException(carDtoRequest.chassi());
        }
    }

    public CarDtoResponse getDtoById(@Valid Long id) {
        try {
            if(carRepository.existsById(id)){
                CarEntity car = carRepository.getReferenceById(id);
                return carDtoResponseMapper.apply(car);
            }

            throw new EntityNotFoundException("Car not found with ID: " + id);

        } catch (MethodArgumentTypeMismatchException ex) {
            throw new IllegalArgumentException("Invalid input or Car not found with ID: " + id, ex);
        }
    }

    public List<CarDtoResponse> getByParams(String vehicle, String brand, Year year) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<CarEntity> carEntitiesPage = null;
        if (year != null) {
            carEntitiesPage =  carRepository.findByVehicleContainingAndBrandContainingAndYear(
                    vehicle != null ? vehicle : "",
                    brand != null ? brand : "",
                    year,
                    pageRequest
            );
        }

        List<CarDtoResponse> carDtoResponses = new ArrayList<>();

        for (CarEntity carEntity : carEntitiesPage.getContent()) {
            CarDtoResponse carDtoResponse = carDtoResponseMapper.apply(carEntity);
            carDtoResponses.add(carDtoResponse);
        }

        return carDtoResponses;
    }

    public CarDtoResponse updateCar(Long id, @Valid CarDtoRequest carDtoRequest) {
        try {
            if(carRepository.existsById(id)){
                CarEntity car = carRepository.getReferenceById(id);

                if(carDtoRequest.vehicle() != null && carDtoRequest.vehicle() != ""){
                    car.setVehicle(carDtoRequest.vehicle());
                }

                if(carDtoRequest.brand() != null && carDtoRequest.brand() != ""){
                    car.setBrand(carDtoRequest.brand());
                }

                if (carDtoRequest.description() != null && carDtoRequest.description() != "") {
                    car.setDescription(carDtoRequest.description());
                }

                if (carDtoRequest.price() != null) {
                    car.setPrice(carDtoRequest.price());
                }

                CarEntity updatedCar = carRepository.save(car);
                return carDtoResponseMapper.apply(updatedCar);
            }

            throw new EntityNotFoundException("Car not found with ID: " + id);

        } catch ( MethodArgumentTypeMismatchException ex) {
            throw new IllegalArgumentException("Invalid input or Car not found with ID: " + id, ex);
        }
    }

    public CarDtoResponse setSold(@Valid Long id){
        try{
            if(carRepository.existsById(id)){
                CarEntity car = carRepository.getReferenceById(id);
                car.setSold(!car.isSold());
                carRepository.save(car);

                return carDtoResponseMapper.apply(car);
            }

            throw new EntityNotFoundException("Car not found with ID: " + id);

        } catch (Exception ex) {
            throw new IllegalArgumentException("Error updating car with ID: " + id, ex);
        }
    }

    public String deleteById(@Valid Long id) {
        try {
            if(carRepository.existsById(id)){
                CarEntity car = carRepository.getReferenceById(id);
                carRepository.delete(car);
                return "Car successfully deleted.";
            }

            throw new EntityNotFoundException("Car not found with ID: " + id);

        } catch ( MethodArgumentTypeMismatchException ex) {
            throw new IllegalArgumentException("Invalid input or Car not found with ID: " + id, ex);
        }
    }
}
