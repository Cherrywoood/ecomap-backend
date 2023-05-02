package com.example.ecomapbackend.service;

import com.example.ecomapbackend.dto.request.CreateMapProviderDto;
import com.example.ecomapbackend.model.MapProvider;

import java.util.List;

public interface MapProviderService {
    void save(CreateMapProviderDto dto);

    List<MapProvider> findAll();

    void updateMainMap(Short id);

    void delete(Short id);
}
