package com.example.ecomapbackend.controller;

import com.example.ecomapbackend.dto.request.CreateOrUpdateEcopointDto;
import com.example.ecomapbackend.dto.response.EcopointFullInfoDto;
import com.example.ecomapbackend.dto.response.EcopointMainInfoDto;
import com.example.ecomapbackend.mapper.EcopointMapper;
import com.example.ecomapbackend.service.EcopointService;
import com.example.ecomapbackend.validator.ImageValidator;
import com.example.ecomapbackend.validator.OnCreate;
import com.example.ecomapbackend.validator.OnUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(params = {"minLon", "minLat", "maxLon", "maxLat"})
    @ResponseStatus(HttpStatus.OK)
    public List<EcopointMainInfoDto> findAllEcopointsWithinScreen(@RequestParam Double minLon, @RequestParam Double minLat,
                                                                  @RequestParam Double maxLon, @RequestParam Double maxLat) {
        return ecopointService.findAllEcopointsWithinScreen(minLon, minLat, maxLon, maxLat).stream()
                .map(ecopointMapper::mapToMainDto)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EcopointFullInfoDto findAllById(@PathVariable Integer id) {
        return ecopointMapper.mapToFullDto(ecopointService.findById(id));
    }

    @GetMapping(value = "/images/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Resource downloadImage(@PathVariable Integer imageId) {
        return ecopointService.findEcopointImageById(imageId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public EcopointFullInfoDto save(@RequestPart("ecopoint")
                                    @Valid CreateOrUpdateEcopointDto dto,
                                    @RequestPart(value = "images", required = false)
                                    @Size(max = 3, message = "max 3 images")
                                    List<MultipartFile> images) {
        if (images != null) {
            images.forEach(ImageValidator::mediaTypeValidate);
        }
        return ecopointMapper.mapToFullDto(ecopointService.save(dto, images));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        ecopointService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Validated(OnUpdate.class)
    public EcopointFullInfoDto update(@PathVariable Integer id,
                                      @RequestPart("ecopoint") @Valid
                                      CreateOrUpdateEcopointDto dto,
                                      @RequestPart(value = "images", required = false)
                                      @Size(max = 5, message = "max 5 images")
                                      List<MultipartFile> images) {
        return ecopointMapper.mapToFullDto(ecopointService.update(id, dto, images));
    }

}
