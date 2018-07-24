package com.zmdev.goldenbag.exception;

/**
 * 驗證錯誤
 */
public class ValidationException extends ServiceException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
