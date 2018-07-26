package com.zmdev.goldenbag.exception;

/**
 * 直接经理已经评价
 */
public class AlreadyScoreException extends ServiceException {
    public AlreadyScoreException() {
    }

    public AlreadyScoreException(String message) {
        super(message);
    }

    public AlreadyScoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
