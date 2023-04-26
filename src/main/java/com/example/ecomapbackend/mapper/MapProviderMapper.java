package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.response.MapProviderInfoDto;
import com.example.ecomapbackend.model.MapProvider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapProviderMapper {
    MapProviderInfoDto map(MapProvider mapProvider);
}
