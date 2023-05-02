package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.CreateOrUpdateOpeningHoursDto;
import com.example.ecomapbackend.dto.response.OpeningHoursInfoDto;
import com.example.ecomapbackend.model.OpeningHours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OpeningHoursMapper {

    @Mapping(target = "ecopoint", ignore = true)
    OpeningHours map(CreateOrUpdateOpeningHoursDto dto);

    OpeningHoursInfoDto map(OpeningHours openingHours);
}
