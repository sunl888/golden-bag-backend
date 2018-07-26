package com.zmdev.goldenbag.exception;

/**
 * 不在考核期内
 */
public class NotInDateOfExaminationException extends ServiceException {
    public NotInDateOfExaminationException() {
    }

    public NotInDateOfExaminationException(String message) {
        super(message);
    }

    public NotInDateOfExaminationException(String message, Throwable cause) {
        super(message, cause);
    }
}
