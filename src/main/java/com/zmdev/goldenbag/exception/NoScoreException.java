package com.zmdev.goldenbag.exception;

/**
 * 直接经理没有评分
 */
public class NoScoreException extends ServiceException {
    public NoScoreException() {
    }

    public NoScoreException(String message) {
        super(message);
    }

    public NoScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
