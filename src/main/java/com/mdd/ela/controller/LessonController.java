package com.mdd.ela.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdd.ela.dto.model.Lesson;
import com.mdd.ela.dto.request.lesson.LessonRequest;
import com.mdd.ela.dto.response.BaseResponse;
import com.mdd.ela.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/lesson")
public class LessonController {

    @Autowired
    private LessonService service;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody LessonRequest request) {
        var response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@PathVariable long id, @RequestBody LessonRequest request) {
        var response = service.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable long id) {
        var response = service.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getDetails(@PathVariable long id) {
        var response = service.getDetail(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getAll(long courseId) {
        var response = service.getAll(courseId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
