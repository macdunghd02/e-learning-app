package com.mdd.ela.dto.lesson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.model.entity.Lesson;

/**
 * @author dungmd
 * @created 1/7/2025 5:53 下午
 * @project e-learning-app
 */
@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "active"})
public class LessonRequest extends Lesson {
}
