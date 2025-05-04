package com.mdd.ela.dto.dashboard;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author dungmd
 * @created 4/17/2025 7:27 下午
 * @project e-learning-app
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueResponse {
    long courseId;
    long revenue;
}
