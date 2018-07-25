package com.zmdev.goldenbag.exception;

/**
 * 身份驗證失敗
 */
public class AuthorizationException extends ServiceException {
    public AuthorizationException() {
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
