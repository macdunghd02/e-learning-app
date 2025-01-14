package com.mdd.ela.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/6/2025 3:37 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chapter {
    long id;
    long courseId;
    int orderNum;
    String title;
}
