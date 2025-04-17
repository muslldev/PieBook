package com.mpbtwozeroone.piebook.responses;

import com.mpbtwozeroone.piebook.model.FileStorage;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {
    private String uploadUrl;
    private Long fileId;
    private String fileKey;
    private FileStorage fileRecord;
}
