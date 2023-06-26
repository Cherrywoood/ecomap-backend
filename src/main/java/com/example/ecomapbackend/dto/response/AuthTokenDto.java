package com.example.ecomapbackend.dto.response;

import com.example.ecomapbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthTokenDto {
    private String token;
    List<String> roles;
}
