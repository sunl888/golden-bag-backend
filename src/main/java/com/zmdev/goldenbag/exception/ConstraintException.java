package com.zmdev.goldenbag.exception;

/**
 * 約束異常(如該記錄被其他記錄約束導致不能直接刪除)
 */
public class ConstraintException extends ServiceException {
    public ConstraintException() {
    }

    public ConstraintException(String message) {
        super(message);
    }

    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
