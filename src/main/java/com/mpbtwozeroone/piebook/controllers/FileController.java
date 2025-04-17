package com.mpbtwozeroone.piebook.controllers;

import com.mpbtwozeroone.piebook.responses.FileUploadResponse;
import com.mpbtwozeroone.piebook.services.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileStorageService fileStorageService;

    @PostMapping("/generate-upload-url")
    public ResponseEntity<FileUploadResponse> generateUploadUrl(
            @RequestParam String source,
            @RequestParam String externalId,
            @RequestParam String filename,
            @RequestParam String fileType) {

        FileUploadResponse response = fileStorageService.generateUploadUrl(
                source, externalId, filename, fileType);

        return ResponseEntity.ok(response);
    }
}