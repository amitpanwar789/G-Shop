package com.gshop.productservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile file) {
        try {
            // Ensure directory exists
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate filename
            String fileName = file.getOriginalFilename();
            String extension = "";
            int i = fileName.lastIndexOf('.');
            if (i >= 0) {
                extension = fileName.substring(i);
            }
            String newFileName = UUID.randomUUID().toString() + extension;

            Path filePath = uploadPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("/" + newFileName); // Node returned absolute path usually /uploads/xxx
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload file");
        }
    }
}
