package com.sagar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FileStorageService {

    @Value("${app.upload.dir}")  // Set default upload dir if not provided
    private String uploadDir;

    public String saveFile(MultipartFile file, String name, String directory) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Create the directory path
            Path dirPath = Paths.get(uploadDir + File.separator + directory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);  // Create directories if they don't exist
            }

            // Detect the file extension
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String extension = getExtensionFromContentType(file.getContentType(), originalFileName);

            // Generate a unique filename with extension
            String fileName = System.currentTimeMillis() + "_" + name + extension;
            Path filePath = dirPath.resolve(fileName);

            // Save the file
            Files.copy(file.getInputStream(), filePath);

            return "/images/" + directory + "/" + fileName;  // Return the relative path
        }
        return null;
    }

    // Helper method to determine the file extension from MIME type
    private String getExtensionFromContentType(String contentType, String originalFileName) {
        if (contentType != null) {
            return switch (contentType) {
                case "image/jpeg" -> ".jpg";
                case "image/png" -> ".png";
                case "image/gif" -> ".gif";
                default -> "";  // Default to no extension if unknown
            };
        }
        // Fallback to using original filename's extension
        return originalFileName.contains(".") ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
    }
}
