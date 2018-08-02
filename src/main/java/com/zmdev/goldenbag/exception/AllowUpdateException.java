package com.zmdev.goldenbag.exception;

public class AllowUpdateException extends ServiceException {
    public AllowUpdateException() {
    }

    public AllowUpdateException(String message) {
        super(message);
    }

    public AllowUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
