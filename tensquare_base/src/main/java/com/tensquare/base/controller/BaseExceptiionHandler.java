package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice
public class BaseExceptiionHandler {

    /**
     * 异常处理方法
     */
   /* @ExceptionHandler(value = NullPointerException.class)      // 只能处理空指针异常处理
    public Result handlerError(){

    }*/

    @ExceptionHandler(value = Exception.class)      // 处理所有异常
    @ResponseBody // 这里没有@RestController，必须加上
    public Result handlerError(Exception e){
        return new Result(false, StatusCode.ERROR,"操作失败："+e.getMessage());
    }
}
