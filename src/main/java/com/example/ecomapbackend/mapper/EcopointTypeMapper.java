package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.response.EcopointTypeInfoDto;
import com.example.ecomapbackend.model.EcopointType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EcopointTypeMapper {
    EcopointTypeInfoDto map(EcopointType ecopointType);
}
