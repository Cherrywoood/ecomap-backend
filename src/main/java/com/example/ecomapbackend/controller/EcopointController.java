package com.example.ecomapbackend.controller;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.dto.response.EcopointFullInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMainInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMapInfoDto;
import com.example.ecomapbackend.dto.response.EcopointSearchInfoDto;
import com.example.ecomapbackend.enums.TimeWork;
import com.example.ecomapbackend.mapper.EcopointMapper;
import com.example.ecomapbackend.service.EcopointService;
import com.example.ecomapbackend.validator.ImageValidator;
import com.example.ecomapbackend.validator.OnCreate;
import com.example.ecomapbackend.validator.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/ecopoints")
public class EcopointController {
    private final EcopointService ecopointService;
    private final EcopointMapper ecopointMapper;

    @GetMapping(params = {"lowerLeftCoordinates", "upperRightCoordinates"})
    @ResponseStatus(HttpStatus.OK)
    public List<EcopointMapInfoDto> findEcopointsMapInBounds(
            @RequestParam double [] lowerLeftCoordinates,
            @RequestParam double [] upperRightCoordinates,
            @RequestParam(required = false) List<Short> ecopointTypes,
            @RequestParam(required = false) List<Short> wasteTypes,
            @RequestParam(required = false) List<Short> shopTypes,
            @RequestParam(required = false) List<Short> eventTypes,
            @RequestParam(required = false, defaultValue = "ALL") TimeWork timeWork) {
        var ecopoints = ecopointService.findAll(lowerLeftCoordinates, upperRightCoordinates, ecopointTypes,
                wasteTypes, shopTypes, eventTypes, timeWork);
        return ecopoints.stream()
                .map(ecopointMapper::mapToMap)
                .toList();
    }

    @GetMapping(value = "/pagination", params = {"lowerLeftCoordinates", "upperRightCoordinates"})
    public ResponseEntity<List<EcopointMainInfoDto>> findEcopointsMainInBoundsPagination(
            @RequestParam int page, @RequestParam int size,
            @RequestParam double [] lowerLeftCoordinates,
            @RequestParam double [] upperRightCoordinates,
            @RequestParam(required = false) List<Short> ecopointTypes,
            @RequestParam(required = false) List<Short> wasteTypes,
            @RequestParam(required = false) List<Short> shopTypes,
            @RequestParam(required = false) List<Short> eventTypes,
            @RequestParam(required = false, defaultValue = "ALL") TimeWork timeWork) {
        var pageEcopoints = ecopointService.findAllPagination(lowerLeftCoordinates, upperRightCoordinates,
                ecopointTypes, wasteTypes, shopTypes, eventTypes, timeWork, PageRequest.of(page, size));
        var ecopointDto = pageEcopoints.getContent().stream()
                .map(ecopointMapper::mapToMainDto)
                .toList();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(pageEcopoints.getTotalElements()))
                .body(ecopointDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EcopointFullInfoDto findAllById(@PathVariable Long id) {
        return ecopointMapper.mapToFullDto(ecopointService.findById(id));
    }

    @GetMapping(value = "/images/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource downloadImage(@PathVariable Long imageId) {
        return ecopointService.findEcopointImageById(imageId);
    }

    @GetMapping(params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public List<EcopointSearchInfoDto> findEcopointsByNameLike(@RequestParam String name) {
        return ecopointService.findEcopointsByNameLike(name).stream()
                .map(ecopointMapper::mapToSearch)
                .toList();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EcopointFullInfoDto save(@RequestPart("ecopoint")
                                    @Valid CreateOrUpdateEcopointDto dto,
                                    @RequestPart(value = "images", required = false)
                                    @Size(max = 10, message = "max 10 images")
                                    List<MultipartFile> images) {
        if (images != null) {
            images.forEach(ImageValidator::mediaTypeValidate);
        }
        return ecopointMapper.mapToFullDto(ecopointService.save(dto, images));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        ecopointService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated(OnUpdate.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EcopointFullInfoDto update(@PathVariable Long id,
                                      @RequestPart("ecopoint") @Valid
                                      CreateOrUpdateEcopointDto dto,
                                      @RequestPart(value = "images", required = false)
                                      @Size(max = 10, message = "max 10 images")
                                      List<MultipartFile> images) {
        return ecopointMapper.mapToFullDto(ecopointService.update(id, dto, images));
    }
}
