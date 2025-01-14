package com.mdd.ela.service;

import com.mdd.ela.dto.course.CourseRequest;
import com.mdd.ela.model.base.APIResponse;

import java.util.Map;

/**
 * @author dungmd
 * @created 1/4/2025 10:14 下午
 * @project e-learning-app
 */

public interface CourseService {
    APIResponse getAll(Map<String,Object> reqMap);
    APIResponse getDetail(long id);
    APIResponse create(CourseRequest request);
    APIResponse update(CourseRequest request);
    APIResponse delete(long id);
}
