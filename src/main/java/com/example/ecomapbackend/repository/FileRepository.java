package com.example.ecomapbackend.repository;

import com.example.ecomapbackend.exception.custom.FileException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Component
public class FileRepository {
    private static final String NOT_UPLOAD_ERROR = "File failed to upload due to internal server error";
    private static final String CREATE_FILE_ERROR = "File failed to created";
    private static final String DELETE_FILE_ERROR = "File failed to deleted";

    private final Path root;

    public FileRepository(@Value("${path.images}") String root) {
        this.root = Path.of(root);
    }

    @PostConstruct
    public void init() {
        if (!Files.exists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException(CREATE_FILE_ERROR);
            }
        }

    }

    public void save(MultipartFile file, String fileName) {
        Path filePath = root.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException(NOT_UPLOAD_ERROR);
        }
    }

    public Optional<Resource> findByName(String filePath) {
        Path fullFilePath = root.resolve(filePath);
        Resource resource = null;

        if (Files.exists(fullFilePath)) {
            resource = new FileSystemResource(fullFilePath);
        }
        return Optional.ofNullable(resource);
    }

    public void delete(String filePath) {
        Path fileFullPath = root.resolve(filePath);
        try {
            Files.deleteIfExists(fileFullPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(DELETE_FILE_ERROR);
        }
    }
}
