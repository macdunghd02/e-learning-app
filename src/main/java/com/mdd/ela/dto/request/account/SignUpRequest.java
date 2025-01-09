package com.mdd.ela.dto.request.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 12/24/2024 3:15 PM
 * @project e-learning-app
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @JsonIgnore
    Long id;
    String password;
    String email;
}
