package com.example.ecomapbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcopointFullInfoDto {
    private Integer id;
    private String address;
    private Point geometry;
    private String name;
    private String photo;
    private String description;
    private String site;
    private String phoneNumber;
    private String email;
    private Boolean convenience;
    private List<OpeningHoursInfoDto> openingHoursInfoDtos;
    private Set<WasteTypeInfoDto> wasteTypes;
    private Set<EcopointTypeInfoDto> ecopointTypes;

}
