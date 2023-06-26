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
public class EcopointMapInfoDto {
    private Long id;
    private String address;
    private Point position;
    private String name;
    private Set<EcopointTypeInfoDto> ecopointTypes;
}
