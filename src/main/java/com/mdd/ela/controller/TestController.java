package com.mdd.ela.controller;

import com.mdd.ela.service.BaseRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author dungmd
 * @created 1/10/2025 11:47 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/test")
public class TestController {
    @Autowired
    BaseRedisService service;
    @PostMapping()
    ResponseEntity<Object> testRedis(@RequestParam String key, @RequestParam String value){
        service.set(key,value);
        return null;
    }
}
