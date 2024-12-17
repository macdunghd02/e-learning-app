package com.mdd.ela.exception;
/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public StorageException(String message) {
        super(message);
    }
    public StorageException(String message,Exception e) {
        super(message,e);
    }
}
