package com.example.ecomapbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMapProviderDto {

    @NotBlank
    @Size(max = 25)
    private String name;

    @NotBlank
    private String url;

    @NotBlank
    private String attribution;
    private String authToken;

    @Builder.Default
    private Boolean isMain = false;
}
