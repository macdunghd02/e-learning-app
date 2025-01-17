package com.mdd.ela.dto.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.model.entity.Course;
import lombok.Data;

/**
 * @author dungmd
 * @created 1/4/2025 10:26 下午
 * @project e-learning-app
 */
@Data
@JsonIgnoreProperties({"id","createUserId", "createTime", "modifyUserId", "modifyTime", "active", "authorAccountId", "courseNote", "avatarId"})
public class CourseRequest extends Course {
    CourseNoteRequest courseNoteRequest;
}
