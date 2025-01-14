package com.mdd.ela.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
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

    @Operation(summary = "Create course", description = "{\n" +
            "  \"courseRequest\": {\n" +
            "    \"title\": \"string\",\n" +
            "    \"description\": \"string\",\n" +
            "    \"avatarUrl\": \"string\",\n" +
            "    \"rootPrice\": 1073741824,\n" +
            "    \"type\": 1073741824,\n" +
            "    \"categoryId\": 9007199254740991,\n" +
            "    \"courseNoteRequest\": {\n" +
            "      \"targetGoal\": [\n" +
            "        \"string\"\n" +
            "      ],\n" +
            "      \"requireSkill\": [\n" +
            "        \"string\"\n" +
            "      ],\n" +
            "      \"suitableFor\": [\n" +
            "        \"string\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> create(@RequestPart String courseRequest, MultipartFile file) throws IOException {
        String avatarUrl = baseS3Service.saveFile(file,"image");
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CourseRequest request = objectMapper.readValue(courseRequest, CourseRequest.class);
        request.setAvatarUrl(avatarUrl);
        return new ResponseEntity<>(service.create(request), HttpStatus.OK);
    }

    @Operation(summary = "Update course", description = "{\n" +
            "  \"courseRequest\": {\n" +
            "    \"title\": \"string\",\n" +
            "    \"description\": \"string\",\n" +
            "    \"avatarUrl\": \"string\",\n" +
            "    \"rootPrice\": 1073741824,\n" +
            "    \"type\": 1073741824,\n" +
            "    \"categoryId\": 9007199254740991,\n" +
            "    \"courseNoteRequest\": {\n" +
            "      \"targetGoal\": [\n" +
            "        \"string\"\n" +
            "      ],\n" +
            "      \"requireSkill\": [\n" +
            "        \"string\"\n" +
            "      ],\n" +
            "      \"suitableFor\": [\n" +
            "        \"string\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}")
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> update(@PathVariable long id, @RequestPart String courseRequest, MultipartFile file) throws JsonProcessingException {
        String avatarUrl = baseS3Service.saveFile(file,"image");
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CourseRequest request = objectMapper.readValue(courseRequest, CourseRequest.class);
        request.setAvatarUrl(avatarUrl);
        request.setId(id);
        return new ResponseEntity<>(service.update(request), HttpStatus.OK);
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
    @GetMapping
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
}
