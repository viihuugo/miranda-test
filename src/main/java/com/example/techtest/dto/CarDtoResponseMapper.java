package com.example.techtest.dto;

import com.example.techtest.models.CarEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CarDtoResponseMapper implements Function<CarEntity, CarDtoResponse> {
    @Override
    public CarDtoResponse apply(CarEntity entity) {
        return new CarDtoResponse(
                entity.getId(),
                entity.getVehicle(),
                entity.getBrand(),
                entity.getYear(),
                entity.getDescription(),
                entity.getChassi(),
                entity.getPrice(),
                entity.isSold(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
