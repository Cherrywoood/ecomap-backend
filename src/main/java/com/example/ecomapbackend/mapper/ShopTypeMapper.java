package com.example.ecomapbackend.mapper;

import com.example.ecomapbackend.dto.response.ShopTypeInfoDto;
import com.example.ecomapbackend.model.ShopType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopTypeMapper {
    ShopTypeInfoDto map(ShopType shopType);
}
