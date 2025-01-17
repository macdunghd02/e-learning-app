package com.mdd.ela.service.base;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.io.IOException;
import java.util.List;

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
    String uploadChunkToS3(String fileName, String uploadId, int chunkIndex, byte[] data, int totalChunk, List<CompletedPart> completedPartList) throws IOException;
}
