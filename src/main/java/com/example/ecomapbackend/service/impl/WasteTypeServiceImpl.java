package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.model.WasteType;
import com.example.ecomapbackend.repository.WasteTypeRepository;
import com.example.ecomapbackend.service.WasteTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WasteTypeServiceImpl implements WasteTypeService {
    private final WasteTypeRepository wasteTypeRepository;

    @Override
    public Set<WasteType> findAllById(List<Short> ids) {
        if (ids != null) {
            return new HashSet<>(wasteTypeRepository.findAllById(ids));
        }
        return Collections.emptySet();
    }
}
