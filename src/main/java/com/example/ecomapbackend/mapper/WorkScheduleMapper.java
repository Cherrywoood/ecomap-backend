package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.request.CreateOrUpdateWorkScheduleDto;
import com.example.ecomapbackend.dto.response.WorkScheduleInfoDto;
import com.example.ecomapbackend.model.WorkSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkScheduleMapper {

    @Mapping(target = "ecopoint", ignore = true)
    WorkSchedule map(CreateOrUpdateWorkScheduleDto dto);

    WorkScheduleInfoDto map(WorkSchedule workSchedule);
}
