package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import com.example.ecomapbackend.mapper.EcopointMapper;
import com.example.ecomapbackend.mapper.OpeningHoursMapper;
import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.repository.EcopointRepository;
import com.example.ecomapbackend.service.EcopointImageService;
import com.example.ecomapbackend.service.EcopointService;
import com.example.ecomapbackend.service.EcopointTypeService;
import com.example.ecomapbackend.service.WasteTypeService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EcopointServiceImpl implements EcopointService {
    private static final String NOT_FOUND_ERROR = "Ecopoint not found by id %d";
    private static final String GEOMETRY_EXISTS_ERROR = "Ecopoint with this geometry already exists";
    private final EcopointRepository ecopointRepository;
    private final WasteTypeService wasteTypeService;
    private final EcopointTypeService ecopointTypeService;
    private final EcopointImageService ecopointImageService;
    private final EcopointMapper ecopointMapper;
    private final OpeningHoursMapper openingHoursMapper;

    @Override
    public Ecopoint findById(Integer id) {
        return ecopointRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR.formatted(id)));
    }

    @Override
    public List<Ecopoint> findAllEcopointsWithinScreen(Double minLon, Double minLat, Double maxLon, Double maxLat) {
        return ecopointRepository.findEcopointsWithinRectangularPolygon(minLon, minLat, maxLon, maxLat);
    }

    @Transactional
    public Ecopoint save(CreateOrUpdateEcopointDto dto, List<MultipartFile> images) {
        if (ecopointRepository.existsByGeometry(dto.getGeometry())) {
            throw new EntityExistsException(GEOMETRY_EXISTS_ERROR);
        }

        final var wasteTypes = wasteTypeService.findAllById(dto.getWasteTypeIds());
        final var ecopointTypes = ecopointTypeService.findAllById(dto.getEcopointTypeIds());

        Ecopoint ecopoint = ecopointMapper.map(dto, wasteTypes, ecopointTypes);
        this.addOpeningHoursToEcopointFromDto(ecopoint, dto);

        ecopoint = ecopointRepository.save(ecopoint);
        ecopointImageService.saveAll(images, ecopoint);

        return ecopoint;
    }

    @Override
    public Resource findEcopointImageById(Integer id) {
        return ecopointImageService.findById(id);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (!ecopointRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_ERROR.formatted(id));
        }
        ecopointRepository.deleteById(id);
        var ecopointImages = ecopointImageService.findAllByEcopointId(id);
        ecopointImageService.deleteAll(ecopointImages);
    }

    @Transactional
    @Override
    public Ecopoint update(Integer id, CreateOrUpdateEcopointDto dto, List<MultipartFile> images) {
        Ecopoint ecopoint = ecopointRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR.formatted(id)));

        if (ecopointRepository.existsByGeometryAndIdNot(dto.getGeometry(), ecopoint.getId())) {
            throw new EntityExistsException(GEOMETRY_EXISTS_ERROR);
        }

        final var wasteTypes = wasteTypeService.findAllById(dto.getWasteTypeIds());
        final var ecopointTypes = ecopointTypeService.findAllById(dto.getEcopointTypeIds());

        ecopointMapper.update(ecopoint, dto, wasteTypes, ecopointTypes);

        ecopoint.removeAllOpeningHours();
        this.addOpeningHoursToEcopointFromDto(ecopoint, dto);

        ecopoint = ecopointRepository.save(ecopoint);
        ecopointImageService.updateAll(images, ecopoint);

        return ecopoint;
    }

    private void addOpeningHoursToEcopointFromDto(Ecopoint ecopoint, CreateOrUpdateEcopointDto dto) {
        for (final var openingHoursDto : dto.getOpeningHoursList()) {
            ecopoint.addOpeningHours(openingHoursMapper.map(openingHoursDto));
        }
    }
}
