package com.mdd.ela.service;

import com.mdd.ela.dto.model.Video;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

public interface VideoService {
    DataResponse findAll(long courseId);
    DataResponse select(long id);
    BaseResponse insert(Video form, MultipartFile file);
    BaseResponse update(Video form, MultipartFile file);
    BaseResponse delete(long id);
}
