package com.vulcan.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码和消息的封装类
 *
 * @author Y
 */
@AllArgsConstructor
@Getter
public class CodeMsg {
    private final Integer code;
    private final String msg;

    // 通用错误码
    public static final CodeMsg SUCCESS = new CodeMsg(200, "操作成功");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500, "服务端异常");
    public static final CodeMsg PARAMETER_ERROR = new CodeMsg(400, "参数错误");
    public static final CodeMsg UNAUTHORIZED = new CodeMsg(401, "未授权");
    public static final CodeMsg FORBIDDEN = new CodeMsg(403, "禁止访问");
    public static final CodeMsg NOT_FOUND = new CodeMsg(404, "资源不存在");

    // 用户相关错误
    public static final CodeMsg USER_NOT_FOUND = new CodeMsg(50001, "用户不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(50002, "密码错误");
    public static final CodeMsg USER_EXISTS = new CodeMsg(50003, "用户名已存在");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(50004, "密码不能为空");
    public static final CodeMsg USERNAME_EMPTY = new CodeMsg(50005, "用户名不能为空");
    public static final CodeMsg REGISTER_FAIL = new CodeMsg(50006, "注册失败");

    /**
     * 自定义错误信息
     * 
     * @param msg 错误信息
     * @return 自定义错误码对象
     */
    public CodeMsg fillMsg(String msg) {
        return new CodeMsg(this.code, msg);
    }
}
