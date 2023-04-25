package com.example.demofile.service;

import com.example.demofile.model.UploadFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    UploadFileResponse storeFile(MultipartFile multipartFile);
    Resource downloadFile(String fileName);
}