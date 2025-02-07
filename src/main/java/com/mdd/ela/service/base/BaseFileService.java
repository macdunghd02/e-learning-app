package com.mdd.ela.service.base;

import java.io.IOException;

/**
 * @author dungmd
 * @created 1/17/2025 3:34 下午
 * @project e-learning-app
 */

public interface BaseFileService {
    String saveTempChunk(String fileName, String uploadId,int chunkIndex,byte[] data) throws IOException;

}
