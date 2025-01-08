package com.mdd.ela.dto.model;

import com.mdd.ela.dto.simple.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/7/2025 11:27 上午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rating extends BaseDto {
    long id;
    long account_id;
    long course_id;
    int rating;
    String comment;
}
