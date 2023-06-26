package com.example.ecomapbackend.service.impl;

import com.example.ecomapbackend.exception.custom.FileException;
import com.example.ecomapbackend.exception.custom.NotFoundException;
import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.model.EcopointImage;
import com.example.ecomapbackend.repository.EcopointImageRepository;
import com.example.ecomapbackend.repository.FileRepository;
import com.example.ecomapbackend.service.EcopointImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EcopointImageServiceImpl implements EcopointImageService {
    private static final String NOT_FOUND_ERROR = "Image not found by id %d";
    private static final String DOWNLOAD_ERROR = "Image failed to download due to internal server error";
    private final EcopointImageRepository ecopointImageRepository;
    private final FileRepository fileRepository;

    @Transactional
    @Override
    public void saveAll(List<MultipartFile> images, Ecopoint ecopoint) {
        if (images != null) {
            var savedImageNames = new ArrayList<String>();
            try {
                images.forEach(image -> {
                    var ecopointImage = this.save(image, ecopoint);
                    savedImageNames.add(ecopointImage.getImagePath());
                });
            } catch (Exception exception) {
                savedImageNames.forEach(fileRepository::delete);
                throw new RuntimeException(exception.getMessage());
            }
        }
    }

    @Override
    public EcopointImage save(MultipartFile image, Ecopoint ecopoint) {
        var imageName = generateUniqueImageName(image);

        final var ecopointImage = EcopointImage.builder()
                .imagePath(imageName)
                .build();
        ecopoint.addEcopointImage(ecopointImage);
        ecopointImageRepository.save(ecopointImage);

        fileRepository.save(image, imageName);
        return ecopointImage;
    }

    @Transactional
    @Override
    public void updateAll(List<MultipartFile> images, Ecopoint ecopoint) {
        var ecopointImages = new ArrayList<>(ecopoint.getEcopointImages());
        ecopointImages.forEach(ecopointImage -> {
            ecopoint.removeEcopointImage(ecopointImage);
            fileRepository.delete(ecopointImage.getImagePath());
        });
        this.saveAll(images, ecopoint);
    }

    @Override
    public Resource findById(Long id) {
        var ecopointImage = ecopointImageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_ERROR.formatted(id)));

        return fileRepository.findByName(ecopointImage.getImagePath())
                .orElseThrow(() -> new FileException(DOWNLOAD_ERROR));
    }

    @Override
    public void deleteAll(List<EcopointImage> images) {
        images.forEach(image -> fileRepository.delete(image.getImagePath()));
    }

    public List<EcopointImage> findAllByEcopointId(Long id) {
        return ecopointImageRepository.findAllByEcopointId(id);
    }

    private String generateUniqueImageName(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        return "%s.%s".formatted(UUID.randomUUID().toString(), fileExtension);
    }
}
