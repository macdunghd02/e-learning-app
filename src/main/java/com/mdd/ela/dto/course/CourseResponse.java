package com.mdd.ela.dto.course;

import com.mdd.ela.dto.chapter.ChapterResponse;
import com.mdd.ela.model.entity.Course;
import com.mdd.ela.dto.rating.RatingResponse;
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
    List<RatingResponse> ratingResponseList;
    String categoryName;
    List<CourseNoteResponse> courseNoteResponseList;
    List<ChapterResponse> chapterResponseList;
    Double ratingAvg;
}
