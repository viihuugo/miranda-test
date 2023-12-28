package com.example.techtest.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "cars")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "vehicle", nullable = false)

    private String vehicle;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "year", nullable = false)
    private Year year;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "chassi", unique = true, nullable = false)
    private String chassi;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "price",  nullable = false)
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Column(name = "is_sold", nullable = false)
    @Value("false")
    private boolean isSold;

    @PrePersist
    private void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

}
