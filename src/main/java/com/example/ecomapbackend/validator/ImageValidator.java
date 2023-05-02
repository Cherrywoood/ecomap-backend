package com.example.ecomapbackend.validator;

import com.example.ecomapbackend.exception.custom.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImageValidator {
    private static final List<String> ALLOWED_MEDIA_TYPES = List.of(MediaType.IMAGE_JPEG_VALUE);

    private ImageValidator() {
    }
    public static void mediaTypeValidate(MultipartFile image) {
        if (image == null) return;

        if (image.getContentType() == null || !ALLOWED_MEDIA_TYPES.contains(image.getContentType())) {
            throw new InvalidMediaTypeException("Invalid media type: %s, expected: %s"
                    .formatted(image.getOriginalFilename(), ALLOWED_MEDIA_TYPES.toString()));
        }
    }


}
