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
            return null;
        }
        return new ResultVo<>(codeMsg);
    }
}
