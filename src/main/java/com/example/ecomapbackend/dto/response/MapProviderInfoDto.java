package com.example.ecomapbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MapProviderInfoDto {
    private Short id;
    private String name;
    private String url;
    private String attribution;
    private String authToken;
    private Boolean isMain;
}
