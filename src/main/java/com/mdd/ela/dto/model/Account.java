package com.mdd.ela.dto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mdd.ela.dto.simple.BaseDto;
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
    String firstName;
    String lastName;
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
