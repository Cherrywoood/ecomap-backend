package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.model.MapProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapProviderRepository extends JpaRepository<MapProvider, Short> {
    Optional<MapProvider> findByIsMainIsTrue();

    boolean existsByName(String name);

    @Query(value = "SELECT mp.isMain FROM MapProvider mp where mp.id = :id")
    boolean isMainById(@Param("id") Short id);
}
