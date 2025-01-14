package com.mdd.ela.dto.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/10/2025 4:11 下午
 * @project e-learning-app
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequest {
String refreshToken;
}
