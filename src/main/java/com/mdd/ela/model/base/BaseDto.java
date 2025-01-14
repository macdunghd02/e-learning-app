package com.mdd.ela.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createTime;
    Long createUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime modifyTime;
    Long modifyUserId;
}
