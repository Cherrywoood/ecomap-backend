package com.example.ecomapbackend.controller;

import com.example.ecomapbackend.dto.request.EcopointSuggestionDto;
import com.example.ecomapbackend.service.EcopointSuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ecopoint-suggestion")
public class EcopointSuggestionController {
    private final EcopointSuggestionService ecopointSuggestionService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void ecopointSuggest(@RequestBody @Valid EcopointSuggestionDto dto) {
        ecopointSuggestionService.suggest(dto);
    }
}
