package com.mdd.ela.dto.base;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 2/12/2025 7:45 下午
 * @project e-learning-app
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComboBoxResponse {
    long id;
    String title;
}
