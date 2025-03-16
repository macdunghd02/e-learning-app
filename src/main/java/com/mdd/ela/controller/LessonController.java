package com.mdd.ela.controller;

import com.mdd.ela.dto.lesson.LessonRequest;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.service.LessonService;
import com.mdd.ela.service.base.BaseS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/5/2025 4:41 下午
 * @project e-learning-app
 */
@Tag(name = "Lesson Controller")
@RestController
@RequestMapping("${api.prefix}/lesson")
public class LessonController {
    @Autowired
    BaseS3Service s3Service;

    @Autowired
    private LessonService service;

    @Operation(summary = "Create lesson")
    @PostMapping()
    public ResponseEntity<APIResponse> create(@RequestBody LessonRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @Operation(summary = "Update lesson")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> update(@PathVariable long id, @RequestBody LessonRequest request) {
        request.setId(id);
        return new ResponseEntity<>(service.update(request), HttpStatus.OK);
    }

    @Operation(summary = "Delete lesson")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> delete(@PathVariable long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Get detail lesson")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getDetail(@PathVariable long id) {
        return new ResponseEntity<>(service.getDetail(id), HttpStatus.OK);
    }


    @Operation(summary = "Get by chapter id")
    @GetMapping("/getByChapterId")
    public ResponseEntity<APIResponse> getByChapterId(long chapterId) {
        return new ResponseEntity<>(service.getAll(chapterId), HttpStatus.OK);
    }


    @Operation(summary = "Get all lesson")
    @GetMapping()
    public ResponseEntity<APIResponse> getAllLessonComboBox(@RequestParam(defaultValue = "10") Integer pageSize,
                                                             @RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(required = false) String lessonTitle,
                                                             @RequestParam(required = false) Long chapterId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("pageNum", pageNum);
        reqMap.put("pageSize", pageSize);
        reqMap.put("lessonTitle", lessonTitle);
        reqMap.put("chapterId", chapterId);
        return new ResponseEntity<>(service.getAllComboBox(reqMap), HttpStatus.OK);
    }
}
