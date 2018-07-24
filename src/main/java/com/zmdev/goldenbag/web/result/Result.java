package com.zmdev.goldenbag.web.result;

/**
 * 统一API响应结果封装
 */
public class Result {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }
}