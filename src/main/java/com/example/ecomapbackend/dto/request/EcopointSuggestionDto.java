package com.example.ecomapbackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EcopointSuggestionDto {

    @NotBlank
    private String firstName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String ecopointName;

    @NotBlank
    private String address;

    @NotBlank
    private String description;

    private String site;

    @NotNull
    private Boolean isOrganization;
}
