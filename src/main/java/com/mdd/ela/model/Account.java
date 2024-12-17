package com.mdd.ela.model;

import com.mdd.ela.dto.BaseDto;
import com.mdd.ela.util.validation.EmailPattern;
import com.mdd.ela.util.validation.PhoneNumberPattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends BaseDto {
    long id;
    @EmailPattern
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    @PhoneNumberPattern
    String phoneNum;
    String address;
    String description;
    Boolean active;
    LocalDateTime deleteTime;
}
