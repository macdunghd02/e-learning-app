package com.mdd.ela.dto.request.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Course;
import com.mdd.ela.dto.request.rating.RatingResultForm;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 10:59 下午
 * @project e-learning-app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseResponse extends Course {
    String authorName;
    long numOfLesson;
    String totalTime;
    List<RatingResultForm> ratingResultFormList;
    String categoryName;
    List<CourseNoteResponse> courseNoteResponseList;
}
