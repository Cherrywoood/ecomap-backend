package com.example.ecomapbackend.dto.response;

import com.example.ecomapbackend.enums.DayWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningHoursInfoDto {
    private Integer id;
    private DayWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean isDayOff;
}
