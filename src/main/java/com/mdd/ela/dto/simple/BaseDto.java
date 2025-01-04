package com.mdd.ela.dto.simple;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * @author dungmd
 * @created 12/17/2024 7:19 PM
 * @project e-learning-app
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseDto {
    LocalDateTime createTime;
    Long createUserId;
    LocalDateTime modifyTime;
    Long modifyUserId;
}
