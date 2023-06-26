package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopTypeRepository extends JpaRepository<ShopType, Short> {
}
