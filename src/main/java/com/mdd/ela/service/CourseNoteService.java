package com.mdd.ela.service;

import com.mdd.ela.dto.request.course.CourseNoteRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dungmd
 * @created 1/12/2025 2:50 下午
 * @project e-learning-app
 */

public interface CourseNoteService {
    void saveCourseNotes(CourseNoteRequest request);

}
