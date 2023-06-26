package com.example.ecomapbackend.controller;

import com.example.ecomapbackend.dto.request.AuthRequestDto;
import com.example.ecomapbackend.dto.response.AuthTokenDto;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import com.example.ecomapbackend.repository.UserRepository;
import com.example.ecomapbackend.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthTokenDto login(@Valid @RequestBody AuthRequestDto authRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword())
        );

        var userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

         String token = jwtTokenProvider.createToken(userDetails.getUsername(), roles);
        return AuthTokenDto.builder().token(token).roles(roles).build();
    }
}
