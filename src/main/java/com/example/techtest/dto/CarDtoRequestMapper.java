package com.example.techtest.dto;

import com.example.techtest.models.CarEntity;
import org.springframework.stereotype.Service;

@Service
public class CarDtoRequestMapper {
    public CarEntity toEntity(CarDtoRequest carDtoRequest) {
        CarEntity carEntity = new CarEntity();
        carEntity.setVehicle(carDtoRequest.vehicle());
        carEntity.setBrand(carDtoRequest.brand());
        carEntity.setYear(carDtoRequest.year());
        carEntity.setDescription(carDtoRequest.description());
        carEntity.setChassi(carDtoRequest.chassi());
        carEntity.setPrice(carDtoRequest.price());

        return carEntity;
    }
}
