package com.mdd.ela.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mdd.ela.dto.model.Video;
import com.mdd.ela.dto.request.course.CourseResultForm;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.dto.response.DataResponse;
import com.mdd.ela.exception.ElaRuntimeException;
import com.mdd.ela.repository.VideoRepository;
import com.mdd.ela.service.VideoService;
import com.mdd.ela.util.LoggedInUserUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/5/2025 4:42 下午
 * @project e-learning-app
 */

@Service
@Transactional(rollbackFor = ElaRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoServiceImpl implements VideoService {
    VideoRepository repository;
    Cloudinary cloudinary;

    public VideoServiceImpl(VideoRepository repository, Cloudinary cloudinary) {
        this.repository = repository;
        this.cloudinary = cloudinary;
    }

    @Override
    public DataResponse findAll(long courseId) {
        try {
            List<Video> videoList = repository.findAll(courseId);
            return DataResponse.builder().data(videoList).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public DataResponse select(long id) {
        try {
            Video video = repository.select(id);
            return DataResponse.builder().data(video).build();
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse insert(Video form, MultipartFile file) {
        try {
            long userId = LoggedInUserUtil.getIdOfLoggedInUser();
            form.setCreateUserId(userId);
            Map r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String fileLocation = (String) r.get("secure_url");
            Double videoLength = (Double) r.get("duration");
            form.setVideoUrl(fileLocation);
            form.setVideoLength(videoLength);
            int res = repository.insert(form);
            if(res != 1)
                throw new ElaRuntimeException("fail");
            return BaseResponse.simpleSuccess("success");
        } catch (Exception e) {
            throw new ElaRuntimeException(e.getMessage());
        }
    }

    @Override
    public BaseResponse update(Video form, MultipartFile file) {
        return null;
    }

    @Override
    public BaseResponse delete(long id) {
        return null;
    }
}
