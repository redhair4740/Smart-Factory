package com.vulcan.entity;

import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity
 * @name: CodeMsg
 * @Date: 2024/4/13 下午10:55
 * @Description //TODO
 */
@Data
public class CodeMsg {

    private Integer code;
    private String msg;
    //使用static的原因是因为直接使用
    public static CodeMsg SUCCESS = new CodeMsg(200,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    private CodeMsg(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

}
