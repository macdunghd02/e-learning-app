package com.mdd.ela.repository;

import com.mdd.ela.dto.model.Account;
import com.mdd.ela.dto.model.Course;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseResultForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 9:53 下午 
 * @project e-learning-app
 */
@Mapper
public interface CourseRepository {
    List<CourseResultForm> findAll();
    CourseResultForm select(long id);
    int insert(CourseInsertForm form);
    int update(CourseUpdateForm form);
    int delete(long id);
}
