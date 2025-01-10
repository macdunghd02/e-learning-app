package com.mdd.ela.service.impl;

import com.cloudinary.Cloudinary;
import com.mdd.ela.dto.request.course.CourseRequest;
import com.mdd.ela.dto.request.course.CourseResponse;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.CourseRepository;
import com.mdd.ela.service.CourseService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 10:15 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {

    CourseRepository repository;

    Cloudinary cloudinary;

    public CourseServiceImpl(CourseRepository repository, Cloudinary cloudinary) {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    @Override
    public DataResponse getAll() {
        try {
            List<CourseResponse> courseList = repository.getAll();
            return DataResponse.builder().data(courseList).build();
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse getDetail(long id) {
        try {
            CourseResponse course = repository.getDetail(id);
            return DataResponse.builder().data(course).build();
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse create(CourseRequest request) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            request.setAuthorAccountId(userId);
            int res = repository.create(request);
            if(res != 1)
                throw new AppRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse update(long id, CourseRequest request) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            request.setModifyUserId(userId);
            request.setId(id);
            int res = repository.update(request);
            if(res != 1)
                throw new AppRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }


    @Override
    public BaseResponse delete(long id) {
        try {
            int res = repository.delete(id);
            if (res != 1)
                throw new AppRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new AppRuntimeException(e.getMessage());
        }
    }


}
