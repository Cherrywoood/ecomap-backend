package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.dto.request.CreateMapProviderDto;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import com.example.ecomapbackend.mapper.MapProviderMapper;
import com.example.ecomapbackend.model.MapProvider;
import com.example.ecomapbackend.repository.MapProviderRepository;
import com.example.ecomapbackend.service.MapProviderService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapProviderServiceImpl implements MapProviderService {
    private static final String NOT_FOUND_PROVIDER_ERROR = "Map provider not found by %d";
    private static final String NAME_EXISTS_ERROR = "Map provider already exists with this name";
    private static final String DELETE_IS_MAIN = "Cannot delete main map provider. Please select another default provider first.";
    private final MapProviderRepository mapProviderRepository;
    private final MapProviderMapper mapProviderMapper;

    @Override
    public List<MapProvider> findAll() {
        return mapProviderRepository.findAll();
    }

    @Transactional
    public void save(CreateMapProviderDto dto) {
        if (mapProviderRepository.existsByName(dto.getName())) {
            throw new EntityExistsException(NAME_EXISTS_ERROR);
        }
        if (dto.getIsMain()) {
            var currentMainProvider = mapProviderRepository.findByIsMainIsTrue().orElseThrow();
            this.updateMainAttr(currentMainProvider, false);
        }
        var mapProvider = mapProviderMapper.map(dto);
        mapProviderRepository.save(mapProvider);
    }

    @Override
    @Transactional
    public void updateMainMap(Short id) {
        var currentMainProvider = mapProviderRepository.findByIsMainIsTrue().orElseThrow();

        if (!currentMainProvider.getId().equals(id)) {
            var newMainProvider = mapProviderRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_PROVIDER_ERROR.formatted(id)));

            this.updateMainAttr(currentMainProvider, false);
            this.updateMainAttr(newMainProvider, true);
        }
    }

    public void delete(Short id) {
        if (!mapProviderRepository.existsById(id)) {
            throw new NotFoundException(NOT_FOUND_PROVIDER_ERROR.formatted(id));
        }

        if (mapProviderRepository.isMainById(id)) {
            throw new IllegalArgumentException(DELETE_IS_MAIN);
        }
        mapProviderRepository.deleteById(id);
    }

    private void updateMainAttr(MapProvider mapProvider, boolean mainValue) {
        mapProvider.setIsMain(mainValue);
        mapProviderRepository.save(mapProvider);
    }
}
