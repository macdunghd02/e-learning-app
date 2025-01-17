package com.mdd.ela.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.model.entity.Lesson;

/**
 * @author dungmd
 * @created 1/16/2025 4:49 下午
 * @project e-learning-app
 */
@JsonIgnoreProperties({"id","chapterId", "createUserId", "createTime", "modifyUserId", "modifyTime", "active"})
public class LessonUpdateRequest extends Lesson {
}
