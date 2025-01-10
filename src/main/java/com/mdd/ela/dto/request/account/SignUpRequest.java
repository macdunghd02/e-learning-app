package com.mdd.ela.dto.request.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

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
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    String phoneNum;
    String address;
}
