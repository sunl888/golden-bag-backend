package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.exception.AuthorizationException;
import com.zmdev.goldenbag.exception.ConstraintException;
import com.zmdev.goldenbag.exception.ServiceException;
import com.zmdev.goldenbag.exception.ValidationException;
import com.zmdev.goldenbag.web.result.Result;
import com.zmdev.goldenbag.web.result.ResultCode;
import com.zmdev.goldenbag.web.result.ResultGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = ConstraintException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result constraintExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.FAIL);
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Result validationExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.VALIDATIONEXCEPTION);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authorizationExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result serviceExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result runtimeExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ResultGenerator.genFailResult(e.getMessage());
    }
}
