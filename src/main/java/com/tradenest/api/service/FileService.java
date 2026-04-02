package com.tradenest.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private static final long MAX_SIZE = 15 * 1024 * 1024; // 5MB

    public String upload(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (file.getSize() > MAX_SIZE) {
            throw new RuntimeException("File too large (max 5MB)");
        }

        String type = file.getContentType();

        if (type == null || !type.startsWith("image/")) {
            throw new RuntimeException("Only image files allowed");
        }

        Path dir = Path.of(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path target = dir.resolve(filename);

        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }
}