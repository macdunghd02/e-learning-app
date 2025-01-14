package com.mdd.ela.controller;

import com.mdd.ela.service.base.BaseRedisService;
import com.mdd.ela.service.base.BaseS3Service;
import com.mdd.ela.service.base.BaseS3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dungmd
 * @created 1/10/2025 11:47 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/test")
public class TestController {
    @Autowired
    BaseRedisService baseRedisService;
    @Autowired
    BaseS3Service baseS3Service;
    @PostMapping()
    ResponseEntity<Object> testRedis(@RequestParam String key, @RequestParam String value){
        baseRedisService.set(key,value);
        return null;
    }

    @PostMapping(value = "test-upload-image-s3", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> testUploadImageS3(MultipartFile file){
        baseS3Service.saveFile(file,"image");
        return null;
    }
}
