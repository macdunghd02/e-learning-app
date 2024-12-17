package com.mdd.ela.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Iterator;
import java.util.List;

/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
@Getter
@Setter
public class ExcelRowValidationException extends RuntimeException {
    private int rowIndex;
    private List<ExcelCellValidationException> cellValidationExceptions;
    private Iterator<Cell> cells;

    public ExcelRowValidationException(int rowIndex, List<ExcelCellValidationException> cellValidationExceptions, Iterator<Cell> cells) {
        this.rowIndex = rowIndex;
        this.cellValidationExceptions = cellValidationExceptions;
        this.cells = cells;
    }
}
