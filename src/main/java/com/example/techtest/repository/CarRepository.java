package com.example.techtest.repository;

import com.example.techtest.models.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Page<CarEntity> findByVehicleContainingAndBrandContainingAndYear(
            String vehicle, String brand, Year year, Pageable pageable);
}

