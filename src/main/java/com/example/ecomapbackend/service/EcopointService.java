package com.example.ecomapbackend.service;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.model.Ecopoint;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EcopointService {

    Ecopoint findById(Integer id);

    List<Ecopoint> findAllEcopointsWithinScreen(Double minLon, Double minLat,
                                                Double maxLon, Double maxLat);

    Ecopoint save(CreateOrUpdateEcopointDto dto, List<MultipartFile> images);

    Resource findEcopointImageById(Integer id);

    void delete(Integer id);

    Ecopoint update(Integer id, CreateOrUpdateEcopointDto dto, List<MultipartFile> images);
}
