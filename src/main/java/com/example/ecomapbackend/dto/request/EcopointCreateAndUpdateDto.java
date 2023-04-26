package com.example.ecomapbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcopointCreateAndUpdateDto {
    private String address;
    private Point geometry;
    private String name;
    private String photo;
    private String description;
    private String site;
    private String phoneNumber;
    private String email;
    private Boolean convenience;
    private List<OpeningHoursCreateAndUpdateDto> openingHoursList;
    private List<Short> wasteTypeIds;
    private List<Short> ecopointTypeIds;
}
