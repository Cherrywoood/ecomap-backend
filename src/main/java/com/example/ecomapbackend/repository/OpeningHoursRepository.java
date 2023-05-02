package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Integer> {
}
