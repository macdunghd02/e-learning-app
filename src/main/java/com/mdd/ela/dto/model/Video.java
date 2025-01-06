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
public class Video extends BaseDto {
    long id;
    long courseId;
    long chapterNum;
    String chapterTitle;
    long videoNum;
    String videoTitle;
    String videoUrl;
    Double videoLength;
    int active;
}
