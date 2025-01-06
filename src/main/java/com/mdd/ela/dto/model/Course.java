package com.mdd.ela.dto.model;

import com.mdd.ela.dto.simple.BaseDto;
import com.mdd.ela.util.validation.EmailPattern;
import com.mdd.ela.util.validation.PhoneNumberPattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

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
    long accountId;
    String description;
    List<String> effects;
    String profileImage;
    int rootPrice;
    int price;
    int active;
    int type;
    long categoryId;
}
