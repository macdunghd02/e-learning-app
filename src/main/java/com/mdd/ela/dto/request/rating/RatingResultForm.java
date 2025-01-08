package com.mdd.ela.dto.request.rating;

import com.mdd.ela.dto.model.Rating;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 1/7/2025 11:35 上午
 * @project e-learning-app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingResultForm extends Rating {
    String accountName;
}
