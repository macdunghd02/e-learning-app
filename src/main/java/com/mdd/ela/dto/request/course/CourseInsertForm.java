package com.mdd.ela.dto.request.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Course;

/**
 * @author dungmd
 * @created 1/4/2025 10:26 下午
 * @project e-learning-app
 */
@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "active", "accountId"})
public class CourseInsertForm extends Course {
}
