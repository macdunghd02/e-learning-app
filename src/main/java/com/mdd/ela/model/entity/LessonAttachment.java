package com.mdd.ela.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/7/2025 11:15 上午
 * @project e-learning-app
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonAttachment {
    long id;
    long lessonId;
    String fileName;
    String fileUrl;
    Double fileSize;
}
