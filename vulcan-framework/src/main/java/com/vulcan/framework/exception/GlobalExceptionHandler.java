package com.vulcan.framework.exception;

import com.vulcan.entity.CodeMsg;
import com.vulcan.entity.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author Y
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVo<Void> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("不支持' {} '请求", e.getMethod());
        return ResultVo.error(new CodeMsg(405, "请求方法不支持"));
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVo<Void> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常:", e);
        return ResultVo.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo<Void> handleException(Exception e) {
        log.error("系统异常:", e);
        return ResultVo.error(CodeMsg.SERVER_ERROR);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败:", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder("参数验证失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }
        return ResultVo.error(new CodeMsg(400, sb.toString()));
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResultVo<Void> handleBindException(BindException e) {
        log.error("参数绑定失败:", e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder("参数绑定失败:");
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }
        return ResultVo.error(new CodeMsg(400, sb.toString()));
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo<Void> handleBusinessException(BusinessException e) {
        log.error("业务异常:", e);
        return ResultVo.error(new CodeMsg(e.getCode(), e.getMessage()));
    }
}