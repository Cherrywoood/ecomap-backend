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
    private Long id;
    private String address;
    private Point position;
    private String name;
    private String description;
    private String site;
    private String phoneNumber;
    private String email;
    private boolean isConvenience;
    private List<WorkScheduleInfoDto> workSchedules;
    private Set<WasteTypeInfoDto> wasteTypes;
    private Set<ShopTypeInfoDto> shopTypes;
    private Set<EventTypeInfoDto> eventTypes;
    private Set<EcopointTypeInfoDto> ecopointTypes;
    private List<Long> ecopointImages;
}
