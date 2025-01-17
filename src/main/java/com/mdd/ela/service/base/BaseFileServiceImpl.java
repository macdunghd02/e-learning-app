package com.mdd.ela.service.base;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author dungmd
 * @created 1/17/2025 3:34 下午
 * @project e-learning-app
 */
@Service
public class BaseFileServiceImpl implements BaseFileService {
    @Override
    public String saveTempChunk(String fileName, String uploadId, int chunkIndex, byte[] data) throws IOException {
        String tempDir = "temp/" + uploadId;
        File dir = new File(tempDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String chunkFilePath = tempDir + "/" + chunkIndex;
        try (FileOutputStream fos = new FileOutputStream(chunkFilePath)) {
            fos.write(data);
        }
        return chunkFilePath;
    }
}
