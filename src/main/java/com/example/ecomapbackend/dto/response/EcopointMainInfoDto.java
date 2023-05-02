package com.example.ecomapbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcopointMainInfoDto {
    private Integer id;
    private String address;
    private Point geometry;
    private String name;
    private String description;
    private Set<WasteTypeInfoDto> wasteTypes;
    private Set<EcopointTypeInfoDto> ecopointTypes;
}
