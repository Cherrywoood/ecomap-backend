package com.example.ecomapbackend.dto.response;

import com.example.ecomapbackend.enums.DayWeek;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "HH:mm", timezone = "UTC")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm", timezone = "UTC")
    private LocalTime closeTime;

    private Boolean isDayOff;
}
