package com.example.mslibrarymanagementsystem.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class FileStorageService {

    private static final Set<String> ALLOWED_CONTENT_TYPES =
            Set.of("image/jpeg", "image/png");

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private final Path uploadPath;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public String storeBookCover(Long bookId, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size must not exceed 5 MB");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException(
                    "Only JPEG and PNG files are allowed"
            );
        }

        String extension = getExtension(file.getContentType());

        String fileName = "book-" + bookId + extension;

        Path targetPath = uploadPath.resolve(fileName).normalize();

        try {
            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            return targetPath.toString();

        } catch (IOException e) {
            throw new RuntimeException("Could not store file", e);
        }
    }

    private String getExtension(String contentType) {
        return switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> throw new IllegalArgumentException(
                    "Unsupported file type"
            );
        };
    }

    public Resource loadBookCover(Long bookId) {

        try {
            Path filePath = Files.list(uploadPath)
                    .filter(path -> path.getFileName()
                            .toString()
                            .startsWith("book-" + bookId + "."))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("Book cover not found"));

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("File is not readable");
            }

            return resource;

        } catch (IOException e) {
            throw new RuntimeException("Could not load file", e);
        }
    }
}