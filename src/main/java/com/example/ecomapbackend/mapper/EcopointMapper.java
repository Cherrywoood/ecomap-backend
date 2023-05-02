package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.dto.response.EcopointFullInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMainInfoDto;
import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.model.EcopointType;
import com.example.ecomapbackend.model.WasteType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {EcopointTypeMapper.class, WasteTypeMapper.class, OpeningHoursMapper.class})
public interface EcopointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ecopointImages", ignore = true)
    @Mapping(target = "openingHoursList", ignore = true)
    Ecopoint map(CreateOrUpdateEcopointDto dto,
                 Set<WasteType> wasteTypes,
                 Set<EcopointType> ecopointTypes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ecopointImages", ignore = true)
    @Mapping(target = "openingHoursList", ignore = true)
    void update(@MappingTarget Ecopoint ecopoint,
                CreateOrUpdateEcopointDto dto,
                Set<WasteType> wasteTypes,
                Set<EcopointType> ecopointTypes);

    EcopointFullInfoDto mapToFullDto(Ecopoint ecopoint);

    EcopointMainInfoDto mapToMainDto(Ecopoint ecopoint);
}
