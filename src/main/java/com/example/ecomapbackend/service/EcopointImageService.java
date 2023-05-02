package com.example.ecomapbackend.service;

import com.example.ecomapbackend.model.Ecopoint;
import com.example.ecomapbackend.model.EcopointImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EcopointImageService {
    void saveAll(List<MultipartFile> images, Ecopoint ecopoint);

    EcopointImage save(MultipartFile image, Ecopoint ecopoint);

    Resource findById(Integer id);

    void deleteAll(List<EcopointImage> images);

    void updateAll(List<MultipartFile> images, Ecopoint ecopoint);

    List<EcopointImage> findAllByEcopointId(Integer id);
}
