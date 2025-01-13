package com.mdd.ela.service.base;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 1/12/2025 5:47 下午
 * @project e-learning-app
 */

public interface BaseFileService {
    String saveImage(MultipartFile image);
}
