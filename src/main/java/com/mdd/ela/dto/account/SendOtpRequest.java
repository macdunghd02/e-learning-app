package com.mdd.ela.dto.account;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/10/2025 8:13 下午
 * @project e-learning-app
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendOtpRequest {
    String email;
}
