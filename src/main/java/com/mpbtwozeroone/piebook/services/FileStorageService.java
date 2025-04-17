package com.mpbtwozeroone.piebook.services;

import com.mpbtwozeroone.piebook.model.FileStorage;
import com.mpbtwozeroone.piebook.repositories.FileStorageRepository;
import com.mpbtwozeroone.piebook.responses.FileUploadResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final MinioClient minioClient;
    private final FileStorageRepository fileStorageRepository;

    public FileUploadResponse generateUploadUrl(String source, String externalId, String fileName, String fileType) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String uniqueKey = source + "/" + externalId + "/" + UUID.randomUUID() + fileExtension;

        try {
            String bucketName = "recipes";
            String uploadUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(uniqueKey)
                            .expiry(1, TimeUnit.HOURS)
                            .build());

            FileStorage fileRecord = FileStorage.builder()
                    .keyS3(uniqueKey)
                    .fileName(fileName)
                    .fileType(fileType)
                    .source(source)
                    .externalId(externalId)
                    .build();

            FileStorage savedRecord = fileStorageRepository.save(fileRecord);

            return FileUploadResponse.builder()
                    .uploadUrl(uploadUrl)
                    .fileId(savedRecord.getId())
                    .fileKey(uniqueKey)
                    .fileRecord(savedRecord)
                    .build();
        } catch (Exception e) {
            log.error("Failed to generate upload URL", e);
            throw new RuntimeException("File upload error", e);
        }
    }

    public void uploadFile(String uploadUrl, MultipartFile file) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(uploadUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", file.getContentType());

            try (OutputStream out = connection.getOutputStream()) {
                out.write(file.getBytes());
            }

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("File upload failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    public void updateFileRecord(FileStorage fileRecord) {
        fileStorageRepository.save(fileRecord);
    }
}