package com.mdd.ela.controller;


import com.mdd.ela.dto.course.CourseRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.service.CourseService;
import com.mdd.ela.service.base.BaseS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/course")
@Tag(name = "Course Controller")
public class CourseController {
    @Autowired
    private CourseService service;
    @Autowired
    private BaseS3Service baseS3Service;

    @Operation(summary = "Create course")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse> createCourse(@RequestPart CourseRequest courseRequest,
                                                    @RequestPart MultipartFile file){
        String avatarUrl = baseS3Service.saveFile(file,"image");
        courseRequest.setAvatarUrl(avatarUrl);
        return new ResponseEntity<>(service.create(courseRequest), HttpStatus.OK);
    }

    @Operation(summary = "Update course")
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<APIResponse> update(@PathVariable long id,
                                              @RequestPart CourseRequest courseRequest,
                                              @RequestPart(required = false) MultipartFile file){
        if(file!=null) {
            String avatarUrl = baseS3Service.saveFile(file, "image");
            courseRequest.setAvatarUrl(avatarUrl);
        }
        courseRequest.setId(id);
        return new ResponseEntity<>(service.update(courseRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete course")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Get course detail")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getCourseDetails(@PathVariable long id) {
        return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all course")
    @GetMapping("/filter")
    public ResponseEntity<APIResponse> getAllCourses(@RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(required = false) String searchingValue,
                                                     @RequestParam(required = false) Long categoryId,
                                                     @RequestParam(required = false) Long authorAccountId,
                                                     @RequestParam(required = false) Long priceFrom,
                                                     @RequestParam(required = false) Long priceTo) {
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("pageNum",pageNum);
        reqMap.put("pageSize",pageSize);
        reqMap.put("searchingValue",searchingValue);
        reqMap.put("categoryId",categoryId);
        reqMap.put("authorAccountId",authorAccountId);
        reqMap.put("priceFrom", priceFrom);
        reqMap.put("priceTo", priceTo);
        return new ResponseEntity<>(service.getAll(reqMap), HttpStatus.OK);
    }

    @Operation(summary = "Get all course")
    @GetMapping("/hv")
    public ResponseEntity<APIResponse> getAllCoursesHv() {
        return new ResponseEntity<>(service.getAllByHV(), HttpStatus.OK);
    }

    @Operation(summary = "Get all course for Combo box")
    @GetMapping("/comboBox")
    public ResponseEntity<APIResponse> getAllCoursesComboBox() {
        return new ResponseEntity<>(service.getAllCoursesComboBox(), HttpStatus.OK);
    }



}
