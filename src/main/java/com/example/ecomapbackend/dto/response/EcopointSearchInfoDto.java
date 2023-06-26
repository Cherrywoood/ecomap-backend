package com.example.ecomapbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcopointSearchInfoDto {
    private Long id;
    private String address;
    private Point position;
    private String name;
}
