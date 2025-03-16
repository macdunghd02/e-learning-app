package com.mdd.ela.controller;

import com.mdd.ela.dto.base.UploadChunkRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.service.base.BaseFileService;
import com.mdd.ela.service.base.BaseS3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.model.CompletedPart;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author dungmd
 * @created 2/9/2025 3:59 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/base")
@Tag(name = "Base Controller")
@Slf4j
public class BaseController {
    @Autowired
    BaseS3Service s3Service;
    @Autowired
    BaseFileService fileService;
    @Autowired
    Executor taskExecutor;

    @PostMapping("/upload-multipart-file-by-chunk")
    public ResponseEntity<APIResponse> uploadMultipartFile(@RequestBody UploadChunkRequest request) throws IOException {
        // Giải mã Base64 an toàn
        byte[] buffer;
        if (request.getData().contains(",")) {
            buffer = Base64.getDecoder().decode(request.getData().split(",")[1]);
        } else {
            buffer = Base64.getDecoder().decode(request.getData());
        }

        // Lưu chunk vào thư mục tạm
        fileService.saveTempChunk(request.getFileName(), request.getUploadId(), request.getChunkIndex(), buffer);

        // Nếu chưa phải chunk cuối, trả về OK ngay
        if (request.getChunkIndex() < request.getTotalChunk() - 1) {
            return new ResponseEntity<>(APIResponse.success(null), HttpStatus.OK);
        }

        // Đảm bảo chỉ một thread chạy ghép file
        taskExecutor.execute(() -> {
            try {
                Map<String,Object> createResponse = s3Service.createMultipartUploadRequest(request.getFileName(),request.getUploadId());
                String key = (String) createResponse.get("key");
                String uploadId = (String) createResponse.get("uploadId");

                List<CompletedPart> completedPartList = s3Service.uploadChunk(key,uploadId,"temp/"+ request.getUploadId(), request.getTotalChunk());
                if(completedPartList.size() == request.getTotalChunk()) {
                    s3Service.completeMultipartUploadRequest(key, uploadId, completedPartList);
                    s3Service.removeTempDir("temp/"+ request.getUploadId());
                }
            } catch (IOException e) {
                log.error("Upload to S3 failed for uploadId: " + request.getUploadId(), e);
            }
        });


        return new ResponseEntity<>(APIResponse.success(request.getChunkIndex()), HttpStatus.OK);
    }
}
