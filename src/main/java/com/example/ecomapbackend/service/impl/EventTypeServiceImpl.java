package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.model.EventType;
import com.example.ecomapbackend.repository.EventTypeRepository;
import com.example.ecomapbackend.service.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventTypeServiceImpl implements EventTypeService {
    private final EventTypeRepository eventTypeRepository;

    @Override
    public Set<EventType> findAllById(List<Short> ids) {
        if (ids != null) {
            return new HashSet<>(eventTypeRepository.findAllById(ids));
        }
        return Collections.emptySet();
    }
}
