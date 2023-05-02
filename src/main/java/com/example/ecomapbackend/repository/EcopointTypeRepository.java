package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.EcopointType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcopointTypeRepository extends JpaRepository<EcopointType, Short> {
}
