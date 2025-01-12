package com.mdd.ela.service;

import com.mdd.ela.dto.response.APIResponse;

import java.util.Map;

/**
 * @author dungmd
 * @created 1/12/2025 4:21 下午
 * @project e-learning-app
 */

public interface CodeService {
    APIResponse getByPCode(Map<String, Object> reqMap);
}
