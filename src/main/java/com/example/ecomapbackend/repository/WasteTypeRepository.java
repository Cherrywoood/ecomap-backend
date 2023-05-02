package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.WasteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WasteTypeRepository extends JpaRepository<WasteType, Short> {
}
