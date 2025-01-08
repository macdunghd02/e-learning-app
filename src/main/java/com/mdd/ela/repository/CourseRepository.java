package com.mdd.ela.repository;

import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.request.course.CourseResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 9:53 下午 
 * @project e-learning-app
 */
@Mapper
public interface CourseRepository {
    List<CourseResponse> getAll();
    CourseResponse getDetail(long id);
    int create(CourseRequest request);
    int update(CourseRequest request);
    int delete(long id);
}
