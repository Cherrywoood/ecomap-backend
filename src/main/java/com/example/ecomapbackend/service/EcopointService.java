package com.example.ecomapbackend.service;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.enums.TimeWork;
import com.example.ecomapbackend.model.Ecopoint;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EcopointService {
    Ecopoint findById(Long id);
    List<Ecopoint> findAll(double[] lowerLeftCoordinates,
                           double[] upperRightCoordinates,
                           List<Short> typeIds, List<Short> wasteIds,
                           List<Short> shopIds, List<Short> eventIds,
                           TimeWork timeWork);
    Page<Ecopoint> findAllPagination(double[] lowerLeftCoordinates,
                                     double[] upperRightCoordinates,
                                     List<Short> typeIds, List<Short> wasteIds,
                                     List<Short> shopIds, List<Short> eventIds,
                                     TimeWork timeWork, Pageable pageable);
    List<Ecopoint> findEcopointsByNameLike(String name);
    Resource findEcopointImageById(Long id);
    Ecopoint save(CreateOrUpdateEcopointDto dto, List<MultipartFile> images);

    void delete(Long id);

    Ecopoint update(Long id, CreateOrUpdateEcopointDto dto, List<MultipartFile> images);
}
