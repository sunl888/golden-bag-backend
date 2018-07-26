package com.zmdev.goldenbag.exception;

/**
 * 当前季度已经提交过考核申请
 */
public class AlreadySubmitedException extends ServiceException {
    public AlreadySubmitedException() {
    }

    public AlreadySubmitedException(String message) {
        super(message);
    }

    public AlreadySubmitedException(String message, Throwable cause) {
        super(message, cause);
    }
}
