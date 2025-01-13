package com.mdd.ela.dto.model;

import com.mdd.ela.dto.simple.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/4/2025 5:44 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course extends BaseDto {
    long id;
    String title;
    long authorAccountId;
    String description;
//    CourseNote courseNote;
    String avatarUrl;
    int rootPrice;
    int active;
    int type;
    long categoryId;
}