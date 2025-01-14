package com.mdd.ela.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/11/2025 8:00 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"id,courseId"})
public class CourseNote {
    Long id;
    long courseId;
    int noteCode;
    String noteInfo;
}
