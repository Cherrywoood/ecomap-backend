package com.example.ecomapbackend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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
public class CreateOrUpdateEcopointDto {

    @NotBlank
    private String address;

    @NotNull
    private Point geometry;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String site;

    @Pattern(regexp = "^\\+7\\s\\d{3}\\s\\d{3}-\\d{2}-\\d{2}$",
            message = "Некорректный формат номера телефона, необходимый формат '+7 XXX XXX-XX-XX'")
    private String phoneNumber;

    @Email
    private String email;

    @Builder.Default
    private Boolean isConvenience = false;

    @Valid
    private List<CreateOrUpdateOpeningHoursDto> openingHoursList;
    private List<Short> wasteTypeIds;

    @NotNull
    @NotEmpty
    private List<Short> ecopointTypeIds;
}
