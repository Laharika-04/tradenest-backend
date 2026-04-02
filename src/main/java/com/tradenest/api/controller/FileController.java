package com.tradenest.api.controller;

import com.tradenest.api.service.FileService;
import com.tradenest.api.util.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ApiResponse<Object> upload(@RequestParam("file") MultipartFile file) {

        try {

            String filename = fileService.upload(file);

            return ApiResponse.ok(
                    "/uploads/" + filename,
                    "File uploaded"
            );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}