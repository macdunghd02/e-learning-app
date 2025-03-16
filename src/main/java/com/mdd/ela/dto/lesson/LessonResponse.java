package com.mdd.ela.dto.lesson;

import com.mdd.ela.dto.lessonvideo.LessonVideoResponse;
import com.mdd.ela.model.entity.Lesson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/7/2025 5:54 下午
 * @project e-learning-app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonResponse extends Lesson {
    LessonVideoResponse lessonVideoResponse;
}
