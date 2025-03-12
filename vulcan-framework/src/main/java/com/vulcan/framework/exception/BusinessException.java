package com.vulcan.framework.exception;

import com.vulcan.domain.entity.CodeMsg;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author Y
 */
public sealed class BusinessException extends RuntimeException permits BusinessException.ValidationException, BusinessException.AuthorizationException, BusinessException.DataException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常信息记录
     */
    @Getter
    private final ErrorInfo errorInfo;
    
    /**
     * 异常信息记录类
     * 
     * @param code 错误码
     * @param message 错误消息
     */
    public record ErrorInfo(Integer code, String message) {}

    /**
     * 构造函数
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.errorInfo = new ErrorInfo(500, message);
    }

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.errorInfo = new ErrorInfo(code, message);
    }

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     * @param e       异常
     */
    public BusinessException(Integer code, String message, Throwable e) {
        super(message, e);
        this.errorInfo = new ErrorInfo(code, message);
    }
    
    /**
     * 获取错误码
     * 
     * @return 错误码
     */
    public Integer getCode() {
        return errorInfo.code();
    }
    
    /**
     * 获取错误消息
     * 
     * @return 错误消息
     */
    @Override
    public String getMessage() {
        return errorInfo.message();
    }
    
    /**
     * 创建业务异常的静态工厂方法
     * 
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
    
    /**
     * 创建业务异常的静态工厂方法
     * 
     * @param code 错误码
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }
    
    /**
     * 创建业务异常的静态工厂方法
     * 
     * @param codeMsg 错误码对象
     * @return 业务异常
     */
    public static BusinessException of(CodeMsg codeMsg) {
        return new BusinessException(codeMsg.getCode(), codeMsg.getMsg());
    }
    
    /**
     * 数据验证异常
     */
    public static final class ValidationException extends BusinessException {
        public ValidationException(String message) {
            super(400, message);
        }
        
        public ValidationException(Integer code, String message) {
            super(code, message);
        }
    }
    
    /**
     * 授权异常
     */
    public static final class AuthorizationException extends BusinessException {
        public AuthorizationException(String message) {
            super(401, message);
        }
        
        public AuthorizationException(Integer code, String message) {
            super(code, message);
        }
    }
    
    /**
     * 数据操作异常
     */
    public static final class DataException extends BusinessException {
        public DataException(String message) {
            super(500100, message);
        }
        
        public DataException(Integer code, String message) {
            super(code, message);
        }
    }
}