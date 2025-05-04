package com.mdd.ela.repository;

import com.mdd.ela.dto.base.ComboBoxResponse;
import com.mdd.ela.dto.course.CourseRequest;
import com.mdd.ela.dto.course.CourseResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/4/2025 9:53 下午 
 * @project e-learning-app
 */
@Mapper
public interface CourseRepository {
    List<CourseResponse> getAll(Map<String,Object> reqMap);
    List<CourseResponse> getPopularCourse();
    List<CourseResponse> getQualityCourse();

    int getCount(Map<String,Object> reqMap);
    CourseResponse getDetail(long id);
    int create(CourseRequest request);
    int update(CourseRequest request);
    int delete(long id);
    List<ComboBoxResponse> getAllForComboBox(long id);
}
