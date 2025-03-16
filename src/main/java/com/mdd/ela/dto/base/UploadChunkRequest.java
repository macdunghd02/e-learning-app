package com.mdd.ela.dto.base;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dungmd
 * @created 2/8/2025 6:15 下午
 * @project e-learning-app
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadChunkRequest {
    String data;
    String fileName;
    String uploadId;
    int chunkIndex;
    int totalChunk;
}
