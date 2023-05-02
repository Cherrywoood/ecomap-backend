package com.example.ecomapbackend.service;

import com.example.ecomapbackend.model.WasteType;

import java.util.List;
import java.util.Set;

public interface WasteTypeService {
    Set<WasteType> findAllById(List<Short> ids);

}
