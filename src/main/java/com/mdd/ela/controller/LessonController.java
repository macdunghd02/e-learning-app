package com.mdd.ela.controller;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.base.BaseResponse;
import com.mdd.ela.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
