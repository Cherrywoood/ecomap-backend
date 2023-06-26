package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Short> {
}
