package com.example.ecomapbackend.dto.request;

import com.example.ecomapbackend.enums.DayWeek;
import com.example.ecomapbackend.validator.OnCreate;
import com.example.ecomapbackend.validator.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateWorkScheduleDto {

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotNull
    private DayWeek day;
    private LocalTime openTime;
    private LocalTime closeTime;

    @Builder.Default
    private Boolean isDayOff = false;
}
