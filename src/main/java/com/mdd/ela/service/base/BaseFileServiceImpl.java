package com.mdd.ela.service.base;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/12/2025 5:47 下午
 * @project e-learning-app
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseFileServiceImpl implements BaseFileService {
    Cloudinary cloudinary;

    public BaseFileServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String saveImage(MultipartFile image) {
        try {
            if (image != null) {
                Map<String, Object> result = this.cloudinary.uploader().upload(image.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                return (String) result.get("secure_url");
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException("Lưu ảnh thất bại", e);
        }
    }
}
