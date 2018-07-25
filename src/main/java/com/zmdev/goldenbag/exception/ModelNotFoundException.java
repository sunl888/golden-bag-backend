package com.zmdev.goldenbag.exception;

/**
 * Model 異常,如: 指定的 User 不存在
 */
public class ModelNotFoundException extends ServiceException {
    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
