package com.example.techtest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

public record CarDtoResponse(
        Long id,
        String vehicle,
        String brand,
        Year year,
        String description,
        String chassi,
        BigDecimal price,
        boolean isSold,
        LocalDateTime createdAt,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime updatedAt

        ) {}
