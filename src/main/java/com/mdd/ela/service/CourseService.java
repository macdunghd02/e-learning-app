package com.mdd.ela.service;

import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;

/**
 * @author dungmd
 * @created 1/4/2025 10:14 下午
 * @project e-learning-app
 */

public interface CourseService {
    DataResponse getAll();
    DataResponse getDetail(long id);
    BaseResponse create(CourseRequest request);
    BaseResponse update(long id,CourseRequest request);
    BaseResponse delete(long id);
}
