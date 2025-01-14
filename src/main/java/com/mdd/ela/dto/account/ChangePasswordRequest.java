package com.mdd.ela.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 12/24/2024 3:18 PM
 * @project e-learning-app
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePasswordRequest {
    @JsonIgnore
    long id;
    String oldPassword;
    String newPassword;
    String confirmNewPassword;
}
