package com.mdd.ela.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdd.ela.model.base.BaseDto;
import com.mdd.ela.util.validation.EmailPattern;
import com.mdd.ela.util.validation.PhoneNumberPattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author dungmd
 * @created 12/5/2024 5:51 AM
 * @project e-learning-app
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseDto {
    long id;
    @EmailPattern
    String email;
    String password;
    String fullName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;
    @PhoneNumberPattern
    String phoneNum;
    String address;
    String description;
    Boolean active;
    LocalDateTime deleteTime;
    String avatarUrl;
    String role;
}
