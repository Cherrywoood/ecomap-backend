package com.example.ecomapbackend.controller;

import com.example.ecomapbackend.dto.request.CreateMapProviderDto;
import com.example.ecomapbackend.dto.response.MapProviderInfoDto;
import com.example.ecomapbackend.mapper.MapProviderMapper;
import com.example.ecomapbackend.service.MapProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/map-providers")
public class MapProviderController {
    private final MapProviderService mapProviderService;
    private final MapProviderMapper mapProviderMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid CreateMapProviderDto dto) {
        mapProviderService.save(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MapProviderInfoDto> findAll() {
        return mapProviderService.findAll().stream()
                .map(mapProviderMapper::map)
                .toList();
    }

    @PatchMapping("/{id}/main")
    @ResponseStatus(HttpStatus.OK)
    public void updateMainMap(@PathVariable Short id) {
        mapProviderService.updateMainMap(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Short id) {
        mapProviderService.delete(id);
    }
}
