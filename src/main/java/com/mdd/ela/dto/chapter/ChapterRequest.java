package com.mdd.ela.dto.chapter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdd.ela.model.entity.Chapter;

/**
 * @author dungmd
 * @created 1/16/2025 3:16 下午
 * @project e-learning-app
 */
@JsonIgnoreProperties({"id"})
public class ChapterRequest extends Chapter {
}
