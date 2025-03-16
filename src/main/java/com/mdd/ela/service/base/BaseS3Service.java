package com.mdd.ela.service.base;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/14/2025 12:12 上午
 * @project e-learning-app
 */

public interface BaseS3Service {
    String saveFile(MultipartFile file,String type);
    byte[] downloadFile(String filename);
    String deleteFile(String filename);
    List<String> listAllFiles();
    Map<String,Object> createMultipartUploadRequest(String fileName, String uploadId);

    List<CompletedPart> uploadChunk(String key, String uploadId, String tempDir, int totalChunk) throws IOException;

    String completeMultipartUploadRequest(String key, String uploadId, List<CompletedPart> completedPartList) throws IOException;

    void removeTempDir(String tempDir) throws IOException;
}
