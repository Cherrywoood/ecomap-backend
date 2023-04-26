package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.OpeningHoursCreateAndUpdateDto;
import com.example.ecomapbackend.dto.response.OpeningHoursInfoDto;
import com.example.ecomapbackend.model.OpeningHours;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OpeningHoursMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ecopoint", ignore = true)
    OpeningHours map(OpeningHoursCreateAndUpdateDto dto);

    OpeningHoursInfoDto map(OpeningHours openingHours);
}
