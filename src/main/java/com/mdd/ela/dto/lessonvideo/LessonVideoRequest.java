package com.mdd.ela.dto.lessonvideo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.entity.LessonVideo;

/**
 * @author dungmd
 * @created 3/13/2025 6:16 下午 
 * @project e-learning-app
 */
@JsonIgnoreProperties({"id","lessonId"})
public class LessonVideoRequest extends LessonVideo {
}
