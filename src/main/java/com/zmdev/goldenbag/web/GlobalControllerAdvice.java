package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.exception.ServiceException;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public Result serviceExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result runtimeExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage());
    }
}
