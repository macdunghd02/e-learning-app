package com.mdd.ela.exception;

import lombok.Getter;

import java.util.List;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
public class ExcelValidationException extends RuntimeException {
    private int numberOfError;
    private int numberOfSuccess;
    private List<ExcelRowValidationException> rowErrorList;

    public ExcelValidationException(int numberOfError, int numberOfSuccess, List<ExcelRowValidationException> cellErrorList) {
        this.numberOfError = numberOfError;
        this.numberOfSuccess = numberOfSuccess;
        this.rowErrorList = cellErrorList;
    }
}
