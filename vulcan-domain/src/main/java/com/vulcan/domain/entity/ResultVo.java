package com.vulcan.domain.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一返回结果封装类
 *
 * @author Y
 */
@Data
@Slf4j
public class ResultVo<T> {

    private Integer code;
    private T data;
    private String message;
    
    private ResultVo(T data) {
        this.code = 200;
        this.data = data;
        this.message = "操作成功";
    }
    
    private ResultVo(T data, String message) {
        this.code = 200;
        this.data = data;
        this.message = message;
    }

    private ResultVo(CodeMsg codeMsg) {
        this.message = codeMsg.getMsg();
        this.code = codeMsg.getCode();
    }
    
    private ResultVo(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功返回结果
     *
     * @param data 返回数据
     * @return 结果
     */
    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(data);
    }
    
    /**
     * 成功返回结果
     *
     * @param data 返回数据
     * @param message 提示信息
     * @return 结果
     */
    public static <T> ResultVo<T> success(T data, String message) {
        return new ResultVo<>(data, message);
    }

    /**
     * 失败返回结果
     *
     * @param codeMsg 错误码对象
     * @return 结果
     */
    public static <T> ResultVo<T> error(CodeMsg codeMsg) {
        if (codeMsg == null) {
            log.warn("codeMsg is null");
            return new ResultVo<>(CodeMsg.SERVER_ERROR);
        }
        return new ResultVo<>(codeMsg);
    }
    
    /**
     * 失败返回结果
     *
     * @param message 错误消息
     * @return 结果
     */
    public static <T> ResultVo<T> error(String message) {
        return new ResultVo<>(500, message);
    }
    
    /**
     * 失败返回结果
     *
     * @param code 错误码
     * @param message 错误消息
     * @return 结果
     */
    public static <T> ResultVo<T> error(Integer code, String message) {
        return new ResultVo<>(code, message);
    }
}
