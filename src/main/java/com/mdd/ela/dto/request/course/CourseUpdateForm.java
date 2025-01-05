package com.mdd.ela.dto.request.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Course;

/**
 * @author dungmd
 * @created 1/5/2025 10:21 上午
 * @project e-learning-app
 */
@JsonIgnoreProperties({"createUserId", "createTime", "modifyUserId", "modifyTime", "accountId"})
public class CourseUpdateForm extends Course{
}
