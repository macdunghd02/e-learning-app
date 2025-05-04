package com.mdd.ela.dto.lesson;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/16/2025 4:05 下午
 * @project e-learning-app@Data
 * @Builder
 * @NoArgsConstructor
 * @AllArgsConstructor
 * @FieldDefaults(level = AccessLevel.PRIVATE)
 */

public class BasicLessonResponse {
    long id;
    String title;
    String url;
    Double duration;
}
