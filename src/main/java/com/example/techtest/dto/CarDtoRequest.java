package com.example.techtest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Year;

public record CarDtoRequest(
        @NotBlank(message = "Vehicle is required")
        String vehicle,
        @NotBlank(message = "Brand is required")
        String brand,
        @NotNull(message = "Year is required")
        @PastOrPresent(message = "The year must be equal to or less than the current year")
        Year year,
        @NotBlank(message = "Description is required")
        String description,
        @NotBlank(message = "Chassi is required")
        @Pattern(regexp = "[a-zA-Z0-9]{17}", message = "Invalid chassi")
        String chassi,
        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price cannot be negative")
        BigDecimal price){}
