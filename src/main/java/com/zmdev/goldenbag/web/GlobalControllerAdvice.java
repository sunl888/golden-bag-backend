package com.zmdev.goldenbag.web;

import com.zmdev.fatesdk.exception.AuthenticationException;
import com.zmdev.goldenbag.exception.*;
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

    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result AuthenticationExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult("请先登录!", ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(value = PermissionException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result PermissionExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult("没有权限!", ResultCode.FAIL);
    }

    @ExceptionHandler(value = ConstraintException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result constraintExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.FAIL);
    }

    @ExceptionHandler(value = AlreadyScoreException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public Result AlreadyScoreExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.FAIL);
    }

    @ExceptionHandler(value = AlreadySubmitedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public Result AlreadySubmitedExceptionHandler(HttpServletRequest req, Exception e) {
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.FAIL);
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Result validationExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.VALIDATIONEXCEPTION);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result authorizationExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return ResultGenerator.genFailResult(e.getMessage(), ResultCode.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result serviceExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
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
