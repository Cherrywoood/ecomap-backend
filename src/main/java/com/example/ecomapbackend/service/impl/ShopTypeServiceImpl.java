package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.model.ShopType;
import com.example.ecomapbackend.model.WasteType;
import com.example.ecomapbackend.repository.ShopTypeRepository;
import com.example.ecomapbackend.repository.WasteTypeRepository;
import com.example.ecomapbackend.service.ShopTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopTypeServiceImpl implements ShopTypeService {
    private final ShopTypeRepository shopTypeRepository;

    @Override
    public Set<ShopType> findAllById(List<Short> ids) {
        if (ids != null) {
            return new HashSet<>(shopTypeRepository.findAllById(ids));
        }
        return Collections.emptySet();
    }
}
