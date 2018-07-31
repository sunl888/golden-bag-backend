package com.zmdev.goldenbag.web.result;


import java.util.Map;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResultData genSuccessResult(T data) {
        return (ResultData) new ResultData<T>(data)
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResultData genSuccessResult(T data, Map<String, Object> meta) {
        return (ResultData) new ResultData<T>(data, meta)
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }

    public static Result genFailResult(String message, ResultCode code) {
        return new Result()
                .setCode(code)
                .setMessage(message);
    }
}