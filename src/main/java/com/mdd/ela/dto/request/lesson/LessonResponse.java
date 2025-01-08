package com.mdd.ela.dto.request.lesson;

import com.mdd.ela.dto.model.Lesson;
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


@FieldDefaults(level = AccessLevel.PRIVATE)

public class LessonResponse extends Lesson {
}
