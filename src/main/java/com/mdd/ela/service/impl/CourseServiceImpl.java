package com.mdd.ela.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mdd.ela.dto.model.Course;
import com.mdd.ela.dto.request.course.CourseInsertForm;
import com.mdd.ela.dto.request.course.CourseResultForm;
import com.mdd.ela.dto.request.course.CourseUpdateForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.repository.CourseRepository;
import com.mdd.ela.service.CourseService;
import com.mdd.ela.util.LoggedInUserUtil;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/4/2025 10:15 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = ElaRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {

    CourseRepository repository;

    Cloudinary cloudinary;

    public CourseServiceImpl(CourseRepository repository, Cloudinary cloudinary) {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    @Override
    public DataResponse findAll() {
        try {
            List<CourseResultForm> courseList = repository.findAll();
            return DataResponse.builder().data(courseList).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse select(long id) {
        try {
            CourseResultForm course = repository.select(id);
            return DataResponse.builder().data(course).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse insert(CourseInsertForm form, MultipartFile image) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            form.setAccountId(userId);
            Map r = this.cloudinary.uploader().upload(image.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String fileLocation = (String) r.get("secure_url");
            form.setProfileImage(fileLocation);
            int res = repository.insert(form);
            if(res != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse update(CourseUpdateForm form, MultipartFile image) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            form.setModifyUserId(userId);
            Map r = this.cloudinary.uploader().upload(image.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String fileLocation = (String) r.get("secure_url");
            form.setProfileImage(fileLocation);
            int res = repository.update(form);
            if(res != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }


    @Override
    public BaseResponse delete(long id) {
        try {
            int res = repository.delete(id);
            if (res != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }


}
