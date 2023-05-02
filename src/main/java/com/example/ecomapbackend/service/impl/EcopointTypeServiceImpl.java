package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.model.EcopointType;
import com.example.ecomapbackend.repository.EcopointTypeRepository;
import com.example.ecomapbackend.service.EcopointTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EcopointTypeServiceImpl implements EcopointTypeService {
    private final EcopointTypeRepository ecopointTypeRepository;

    @Override
    public Set<EcopointType> findAllById(List<Short> ids) {
        if (ids != null) {
            return new HashSet<>(ecopointTypeRepository.findAllById(ids));
        }
        return Collections.emptySet();
    }
}
