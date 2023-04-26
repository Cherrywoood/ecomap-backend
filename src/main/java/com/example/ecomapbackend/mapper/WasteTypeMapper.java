package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.response.WasteTypeInfoDto;
import com.example.ecomapbackend.model.WasteType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WasteTypeMapper {
    WasteTypeInfoDto map(WasteType wasteType);
}
