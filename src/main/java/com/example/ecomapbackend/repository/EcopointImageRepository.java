package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.EcopointImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EcopointImageRepository extends JpaRepository<EcopointImage, Long> {
    List<EcopointImage> findAllByEcopointId(Long id);
}
