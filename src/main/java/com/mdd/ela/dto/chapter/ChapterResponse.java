package com.mdd.ela.dto.chapter;

import com.mdd.ela.dto.lesson.BasicLessonResponse;
import com.mdd.ela.dto.lesson.LessonResponse;
import com.mdd.ela.model.entity.Chapter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author dungmd
 * @created 1/16/2025 3:16 下午
 * @project e-learning-app
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterResponse extends Chapter {
    long numOfLessonInChapter;
    String totalTimeInChapter;
    List<BasicLessonResponse> lessonResponseList;
}
