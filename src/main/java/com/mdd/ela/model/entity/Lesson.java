package com.mdd.ela.model.entity;

import com.mdd.ela.model.base.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
