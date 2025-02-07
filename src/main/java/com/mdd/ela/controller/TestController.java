package com.mdd.ela.controller;

import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.service.base.BaseFileService;
import com.mdd.ela.service.base.BaseRedisService;
import com.mdd.ela.service.base.BaseS3Service;
import com.mdd.ela.service.base.BaseS3ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author dungmd
 * @created 1/10/2025 11:47 下午
 * @project e-learning-app
 */
@Slf4j
@RestController
@RequestMapping("${api.prefix}/test")
public class TestController {
    @Autowired
    BaseRedisService baseRedisService;
    @Autowired
    BaseS3Service baseS3Service;
    @Autowired
    BaseFileService baseFileService;
    @Autowired
    private Executor taskExecutor;

    @PostMapping()
    ResponseEntity<Object> testRedis(@RequestParam String key, @RequestParam String value) {
        baseRedisService.set(key, value);
        return null;
    }

    @PostMapping(value = "test-upload-image-s3", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> testUploadImageS3(MultipartFile file) {
        baseS3Service.saveFile(file, "image");
        return null;
    }

    @PostMapping("/chunk")
    public ResponseEntity<APIResponse> uploadChunk(
            @RequestParam("data") String data,
            @RequestParam("fileName") String fileName,
            @RequestParam("uploadId") String uploadId,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("totalChunk") int totalChunk
    ) throws IOException {
        // Giải mã Base64 an toàn
        byte[] buffer;
        if (data.contains(",")) {
            buffer = Base64.getDecoder().decode(data.split(",")[1]);
        } else {
            buffer = Base64.getDecoder().decode(data);
        }

        // Lưu chunk vào thư mục tạm
        baseFileService.saveTempChunk(fileName, uploadId, chunkIndex, buffer);

        // Nếu chưa phải chunk cuối, trả về OK ngay
        if (chunkIndex < totalChunk - 1) {
            return new ResponseEntity<>(APIResponse.success(null), HttpStatus.OK);
        }

        // Đảm bảo chỉ một thread chạy ghép file
        synchronized (uploadId.intern()) {
            taskExecutor.execute(() -> {
                try {
                    List<CompletedPart> completedPartList = new ArrayList<>();
                    baseS3Service.uploadChunkToS3(fileName, uploadId, "temp/" + uploadId, totalChunk, completedPartList);
                } catch (IOException e) {
                    log.error("Upload to S3 failed for uploadId: " + uploadId, e);
                }
            });
        }

        return new ResponseEntity<>(APIResponse.success(chunkIndex), HttpStatus.OK);
    }
}

