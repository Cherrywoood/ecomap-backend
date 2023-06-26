package com.example.ecomapbackend.service;

import com.example.ecomapbackend.model.ShopType;

import java.util.List;
import java.util.Set;

public interface ShopTypeService {
    Set<ShopType> findAllById(List<Short> ids);
}
