package com.mdd.ela.dto.request.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.dto.model.Course;
import lombok.Data;

/**
 * @author dungmd
 * @created 1/4/2025 10:26 下午
 * @project e-learning-app
 */
@Data
@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "active", "authorAccountId", "courseNote"})
public class CourseRequest extends Course {
    CourseNoteRequest courseNoteRequest;
}
