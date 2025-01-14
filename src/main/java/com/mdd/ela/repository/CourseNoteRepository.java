package com.mdd.ela.repository;

import com.mdd.ela.model.entity.CourseNote;
import com.mdd.ela.dto.course.CourseNoteResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dungmd
 * @created 1/11/2025 8:16 下午
 * @project e-learning-app
 */
@Mapper
public interface CourseNoteRepository {
    List<CourseNoteResponse> getAllByCourseId(long courseId);
    int insertCourseNote(List<CourseNote> courseNoteList);
    int deleteByCourseId(long courseId);
}
