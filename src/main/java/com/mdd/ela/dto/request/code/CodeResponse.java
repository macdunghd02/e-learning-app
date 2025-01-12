package com.mdd.ela.dto.request.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dungmd
 * @created 1/12/2025 4:32 下午
 * @project e-learning-app
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeResponse {
    private Integer id;
    private String code;
    private String desc;
}
