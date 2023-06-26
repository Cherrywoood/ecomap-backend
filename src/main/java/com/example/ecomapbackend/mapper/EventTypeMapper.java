package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.response.EventTypeInfoDto;
import com.example.ecomapbackend.model.EventType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventTypeMapper {
    EventTypeInfoDto map(EventType eventType);
}
