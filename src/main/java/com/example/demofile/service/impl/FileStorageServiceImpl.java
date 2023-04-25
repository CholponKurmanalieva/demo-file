package com.example.demofile.service.impl;

import com.example.demofile.configuration.FileConfiguration;
import com.example.demofile.exception.FileNotFound;
import com.example.demofile.exception.FileStorageException;
import com.example.demofile.model.UploadFileResponse;
import com.example.demofile.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path fileStoragePath;

    public FileStorageServiceImpl(FileConfiguration fileConfiguration) {
        this.fileStoragePath = Paths.get(fileConfiguration.getUpload()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStoragePath);
        } catch (IOException ex) {
            throw new FileStorageException("Directory is noy created");
        }
    }

    @Override
    public UploadFileResponse storeFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try {
            if (fileName.contains(".."))
                throw new FileStorageException("File name is incorrect " + fileName);

            Path pathForSave = this.fileStoragePath.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), pathForSave, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new FileStorageException("Could not file store " + fileName);
        }

        String uriForDownload = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(fileName)
                .toUriString();

        UploadFileResponse response = UploadFileResponse.builder()
                .fileName(fileName)
                .uriForDownloadFile(uriForDownload)
                .size(multipartFile.getSize())
                .fileType(multipartFile.getContentType())
                .build();

        return response;
    }

    @Override
    public Resource downloadFile(String fileName) {
        try {
            Path filePath = this.fileStoragePath.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists())
                return resource;
            else
                throw new FileNotFound("File is not fount " + fileName);
        } catch (MalformedURLException ex) {

        }
        return null;
    }
}