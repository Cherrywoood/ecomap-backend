package com.example.ecomapbackend.service;

import com.example.ecomapbackend.model.EventType;

import java.util.List;
import java.util.Set;

public interface EventTypeService {
    Set<EventType> findAllById(List<Short> ids);
}
