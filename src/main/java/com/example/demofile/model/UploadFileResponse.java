package com.example.demofile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileResponse {
    private String fileName;
    private String uriForDownloadFile;
    private String fileType;
    private long size;
}