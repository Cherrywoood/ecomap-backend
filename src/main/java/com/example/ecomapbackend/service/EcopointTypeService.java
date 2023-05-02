package com.example.ecomapbackend.service;

import com.example.ecomapbackend.model.EcopointType;

import java.util.List;
import java.util.Set;

public interface EcopointTypeService {
    Set<EcopointType> findAllById(List<Short> ids);
}
