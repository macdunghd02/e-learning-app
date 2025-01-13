package com.mdd.ela.controller;

import com.mdd.ela.dto.response.APIResponse;
import com.mdd.ela.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Options;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/12/2025 4:21 下午
 * @project e-learning-app
 */
@RestController
@RequestMapping("${api.prefix}/code")
@Tag(name = "Code Controller")
public class CodeController {
    private CodeService service;

    public CodeController(CodeService service) {
        this.service = service;
    }

    @Operation(summary = "Get code by pCode")
    @GetMapping(value = "/list")
    public ResponseEntity<APIResponse> list(
            @RequestParam(value = "pCode") String pCode) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("pCode", pCode);
        return new ResponseEntity<>(service.getByPCode(reqMap), HttpStatus.OK);
    }
}
