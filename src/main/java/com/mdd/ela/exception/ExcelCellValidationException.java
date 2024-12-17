package com.mdd.ela.exception;

import lombok.Getter;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
public class ExcelCellValidationException extends RuntimeException {
    private Integer rowIndex;
    private String errorDetail;
    private String columnName;

    public ExcelCellValidationException(Integer rowIndex, String columnName, String errorDetail) {
        this.rowIndex = rowIndex;
        this.columnName = columnName;
        this.errorDetail = errorDetail;
    }
}
