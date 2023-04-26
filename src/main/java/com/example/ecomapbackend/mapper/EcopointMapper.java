package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.EcopointCreateAndUpdateDto;
import com.example.ecomapbackend.dto.response.EcopointFullInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMainInfoDto;
import com.example.ecomapbackend.model.Ecopoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EcopointTypeMapper.class, WasteTypeMapper.class, OpeningHoursMapper.class})
public interface EcopointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "wasteTypes", ignore = true)
    @Mapping(target = "ecopointTypes", ignore = true)
    Ecopoint map(EcopointCreateAndUpdateDto dto);

    @Mapping(target = "photo", ignore = true)
    EcopointFullInfoDto mapToFull(Ecopoint ecopoint);

    EcopointMainInfoDto mapToMain(Ecopoint ecopoint);
}
