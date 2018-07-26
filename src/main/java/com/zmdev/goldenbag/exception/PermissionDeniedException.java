package com.zmdev.goldenbag.exception;

/**
 * 没有权限
 */
public class PermissionDeniedException extends ServiceException {
    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
