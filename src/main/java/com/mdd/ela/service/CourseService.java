package com.mdd.ela.service;

import com.mdd.ela.dto.model.Course;
import com.mdd.ela.dto.request.AuthenticationReq;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.nimbusds.jose.JOSEException;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 1/4/2025 10:14 下午
 * @project e-learning-app
 */

public interface CourseService {
    DataResponse findAll();
    DataResponse select(long id);
    BaseResponse insert(CourseInsertForm form, MultipartFile image);
    BaseResponse update(CourseUpdateForm form, MultipartFile image);
    BaseResponse delete(long id);
}
