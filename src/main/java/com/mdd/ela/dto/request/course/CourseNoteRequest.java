package com.mdd.ela.dto.request.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.CourseNote;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author dungmd
 * @created 1/11/2025 5:33 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseNoteRequest {
    @JsonIgnore
    long courseId;
    List<String> targetGoal;
    List<String> requireSkill;
    List<String> suitableFor;
}