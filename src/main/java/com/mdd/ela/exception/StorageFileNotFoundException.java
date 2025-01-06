package com.mdd.ela.exception;
/**
 * @author dungmd
 * @created 12/5/2024 4:32 AM
 * @project e-learning-app
 */
public class StorageFileNotFoundException extends StorageException{
    private static final long serialVersionUID = 1L;
    public StorageFileNotFoundException(String message) {
        super(message);
    }
}
