package com.mdd.ela.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.lessonvideo.LessonVideoRequest;
import com.mdd.ela.model.entity.Lesson;
import com.mdd.ela.model.entity.LessonVideo;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/7/2025 5:53 下午
 * @project e-learning-app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "active"})
public class LessonRequest extends Lesson {
    LessonVideoRequest lessonVideoRequest;
}
