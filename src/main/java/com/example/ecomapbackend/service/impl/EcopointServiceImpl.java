package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.enums.TimeWork;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import com.example.ecomapbackend.mapper.EcopointMapper;
import com.example.ecomapbackend.mapper.WorkScheduleMapper;
import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.repository.EcopointRepository;
import com.example.ecomapbackend.service.*;
import jakarta.persistence.EntityExistsException;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.ecomapbackend.repository.specification.EcopointSpecifications.*;

@Service
@RequiredArgsConstructor
public class EcopointServiceImpl implements EcopointService {
    private static final String NOT_FOUND_ERROR = "Ecopoint not found by id %d";
    private static final String GEOMETRY_EXISTS_ERROR = "Ecopoint with this geometry already exists";
    private final EcopointRepository ecopointRepository;
    private final WasteTypeService wasteTypeService;
    private final ShopTypeService shopTypeService;
    private final EventTypeService eventTypeService;
    private final EcopointTypeService ecopointTypeService;
    private final EcopointImageService ecopointImageService;
    private final EcopointMapper ecopointMapper;
    private final WorkScheduleMapper workScheduleMapper;

    @Override
    public Ecopoint findById(Long id) {
        return ecopointRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR.formatted(id)));
    }

    @Override
    public List<Ecopoint> findAll(double[] lowerLeftCoordinates,
                                  double[] upperRightCoordinates,
                                  List<Short> typeIds, List<Short> wasteIds,
                                  List<Short> shopIds, List<Short> eventIds,
                                  TimeWork timeWork) {
        double minLat = lowerLeftCoordinates[0];
        double minLon = lowerLeftCoordinates[1];
        double maxLat = upperRightCoordinates[0];
        double maxLon = upperRightCoordinates[1];
       return ecopointRepository.findAll(withinBoundingBox(minLon, minLat, maxLon, maxLat)
               .and(byEcopointTypeIn(typeIds))
               .and(byWasteTypeIn(wasteIds))
               .and(byShopTypeIn(shopIds))
               .and(byEventTypeIn(eventIds))
               .and(byEcopointTimeWork(timeWork))
               .and(distinct()));
    }

    @Override
    public Page<Ecopoint> findAllPagination(double [] lowerLeftCoordinates,
                                            double [] upperRightCoordinates,
                                            List<Short> typeIds, List<Short> wasteIds,
                                            List<Short> shopIds, List<Short> eventIds,
                                            TimeWork timeWork, Pageable pageable) {
        double minLat = lowerLeftCoordinates[0];
        double minLon = lowerLeftCoordinates[1];
        double maxLat = upperRightCoordinates[0];
        double maxLon = upperRightCoordinates[1];

        return ecopointRepository.findAll(
                withinBoundingBox(minLon, minLat, maxLon, maxLat)
                        .and(byEcopointTypeIn(typeIds))
                        .and(byWasteTypeIn(wasteIds))
                        .and(byShopTypeIn(shopIds))
                        .and(byEventTypeIn(eventIds))
                        .and(byEcopointTimeWork(timeWork))
                        .and(distinct()), pageable);
    }

    @Override
    public List<Ecopoint> findEcopointsByNameLike(String name) {
        return ecopointRepository.findEcopointsByNameContainsIgnoreCase(name);
    }

    @Transactional
    public Ecopoint save(CreateOrUpdateEcopointDto dto, List<MultipartFile> images) {
        if (ecopointRepository.existsByPosition(dto.getPosition())) {
            throw new EntityExistsException(GEOMETRY_EXISTS_ERROR);
        }

        final var wasteTypes = wasteTypeService.findAllById(dto.getWasteTypeIds());
        final var shopTypes = shopTypeService.findAllById(dto.getShopTypeIds());
        final var eventTypes = eventTypeService.findAllById(dto.getEventTypeIds());
        final var ecopointTypes = ecopointTypeService.findAllById(dto.getEcopointTypeIds());

        Ecopoint ecopoint = ecopointMapper.map(dto, wasteTypes, shopTypes, eventTypes, ecopointTypes);
        this.addWorkSchedulesToEcopointFromDto(ecopoint, dto);

        ecopoint.getPosition().setSRID(3857);
        ecopoint = ecopointRepository.save(ecopoint);
        ecopointImageService.saveAll(images, ecopoint);

        return ecopoint;
    }

    @Override
    public Resource findEcopointImageById(Long id) {
        return ecopointImageService.findById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!ecopointRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_ERROR.formatted(id));
        }
        ecopointRepository.deleteById(id);
        var ecopointImages = ecopointImageService.findAllByEcopointId(id);
        ecopointImageService.deleteAll(ecopointImages);
    }

    @Transactional
    @Override
    public Ecopoint update(Long id, CreateOrUpdateEcopointDto dto, List<MultipartFile> images) {
        Ecopoint ecopoint = ecopointRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR.formatted(id)));

        if (ecopointRepository.existsByPositionAndIdNot(dto.getPosition(), ecopoint.getId())) {
            throw new EntityExistsException(GEOMETRY_EXISTS_ERROR);
        }

        final var wasteTypes = wasteTypeService.findAllById(dto.getWasteTypeIds());
        final var shopTypes = shopTypeService.findAllById(dto.getShopTypeIds());
        final var eventTypes = eventTypeService.findAllById(dto.getEventTypeIds());
        final var ecopointTypes = ecopointTypeService.findAllById(dto.getEcopointTypeIds());

        ecopointMapper.update(ecopoint, dto, wasteTypes, shopTypes, eventTypes, ecopointTypes);

        ecopoint.getPosition().setSRID(3857);
        ecopoint.removeAllWorkSchedule();
        this.addWorkSchedulesToEcopointFromDto(ecopoint, dto);

        ecopoint = ecopointRepository.save(ecopoint);
        ecopointImageService.updateAll(images, ecopoint);

        return ecopoint;
    }

    private void addWorkSchedulesToEcopointFromDto(Ecopoint ecopoint, CreateOrUpdateEcopointDto dto) {
        for (final var workSchedules : dto.getWorkSchedules()) {
            ecopoint.addWorkSchedule(workScheduleMapper.map(workSchedules));
        }
    }
}
