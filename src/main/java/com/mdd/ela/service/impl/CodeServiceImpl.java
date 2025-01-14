package com.mdd.ela.service.impl;

import com.mdd.ela.dto.code.CodeResponse;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.repository.CodeRepository;
import com.mdd.ela.service.CodeService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/12/2025 4:21 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CodeServiceImpl implements CodeService {
    CodeRepository repository;

    public CodeServiceImpl(CodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public APIResponse getByPCode(Map<String, Object> reqMap) {
        List<CodeResponse> codeResponses = repository.getByPCode(reqMap);
        return APIResponse.success(codeResponses);
    }
}
