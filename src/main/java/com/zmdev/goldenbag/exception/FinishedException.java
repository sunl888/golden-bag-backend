package com.zmdev.goldenbag.exception;

/**
 * 考核流程已经完成
 */
public class FinishedException extends ServiceException {
    public FinishedException() {
    }

    public FinishedException(String message) {
        super(message);
    }

    public FinishedException(String message, Throwable cause) {
        super(message, cause);
    }
}
