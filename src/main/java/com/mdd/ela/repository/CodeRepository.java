package com.mdd.ela.repository;

import com.mdd.ela.dto.request.code.CodeResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/12/2025 4:20 下午
 * @project e-learning-app
 */
@Mapper
public interface CodeRepository {
    List<CodeResponse> getByPCode(Map<String,Object> reqMap);
}
