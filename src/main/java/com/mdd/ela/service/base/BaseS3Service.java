package com.mdd.ela.service.base;

import org.springframework.web.multipart.MultipartFile;

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
}
