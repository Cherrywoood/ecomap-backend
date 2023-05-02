package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.CreateMapProviderDto;
import com.example.ecomapbackend.dto.response.MapProviderInfoDto;
import com.example.ecomapbackend.model.MapProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapProviderMapper {
    MapProviderInfoDto map(MapProvider mapProvider);

    @Mapping(target = "id", ignore = true)
    MapProvider map(CreateMapProviderDto dto);
}
