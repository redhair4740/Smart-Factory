package com.vulcan.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity
 * @name: ResultVo
 * @Date: 2024/4/13 下午10:53
 * @Description //TODO
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
        this.message= message;
    }

    private ResultVo(CodeMsg codeMsg) {
        this.message=codeMsg.getMsg();
        this.code=codeMsg.getCode();
    }

    //通过构造方法只需要传data就可以
    public static <T>ResultVo<T> success( T data ){
        return new ResultVo<>(data);
    }

    // 这里使用codeMsg 是因为返回结果的是code码不是固定的
    public static <T>ResultVo<T> error(CodeMsg codeMsg){
        if (codeMsg == null) {
            log.warn("codeMsg is null");
            return null;
        }
        return new ResultVo<T>(codeMsg);
    }
}
