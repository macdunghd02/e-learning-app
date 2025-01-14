package com.mdd.ela.service;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.base.BaseResponse;
import com.mdd.ela.model.base.DataResponse;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

public interface LessonService {
    DataResponse getAll(long courseId);
    DataResponse getDetail(long id);
    BaseResponse create(LessonRequest request);
    BaseResponse update(long id,LessonRequest request);
    BaseResponse delete(long id);
}
