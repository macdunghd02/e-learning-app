package com.mdd.ela.dto.model;

import com.mdd.ela.dto.simple.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

/**
 * @author dungmd
 * @created 1/4/2025 5:46 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson extends BaseDto {
    long id;
    long chapterId;
    String title;
    String description;
    String orderNum;
    int active;
}