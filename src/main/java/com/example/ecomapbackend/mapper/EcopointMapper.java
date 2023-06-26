package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.dto.response.EcopointFullInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMainInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMapInfoDto;
import com.example.ecomapbackend.dto.response.EcopointSearchInfoDto;
import com.example.ecomapbackend.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {EcopointTypeMapper.class,
        WasteTypeMapper.class, ShopTypeMapper.class, EventTypeMapper.class,
        WorkScheduleMapper.class})
public interface EcopointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ecopointImages", ignore = true)
    @Mapping(target = "workSchedules", ignore = true)

    Ecopoint map(CreateOrUpdateEcopointDto dto,
                 Set<WasteType> wasteTypes,
                 Set<ShopType> shopTypes,
                 Set<EventType> eventTypes,
                 Set<EcopointType> ecopointTypes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ecopointImages", ignore = true)
    @Mapping(target = "workSchedules", ignore = true)
    void update(@MappingTarget Ecopoint ecopoint,
                CreateOrUpdateEcopointDto dto,
                Set<WasteType> wasteTypes,
                Set<ShopType> shopTypes,
                Set<EventType> eventTypes,
                Set<EcopointType> ecopointTypes);

    @Mapping(target = "ecopointImages",
            expression = "java(ecopoint.getEcopointImages().stream().map(ecopointImage -> ecopointImage.getId()).toList())")
    EcopointFullInfoDto mapToFullDto(Ecopoint ecopoint);



    @Mapping(target = "countImages", expression = "java(ecopoint.getEcopointImages().size())")
    EcopointMainInfoDto mapToMainDto(Ecopoint ecopoint);

    EcopointSearchInfoDto mapToSearch(Ecopoint ecopoint);

    EcopointMapInfoDto mapToMap(Ecopoint ecopoint);
}
