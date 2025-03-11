package com.vulcan.entity;

import lombok.Data;

/**
 * 响应状态码和消息封装
 *
 * @author Y
 */
@Data
public class CodeMsg {

    private Integer code;
    private String msg;
    
    // 通用响应码
    public static final CodeMsg SUCCESS = new CodeMsg(200, "操作成功");
    public static final CodeMsg PARAM_ERROR = new CodeMsg(400, "请求参数错误");
    public static final CodeMsg UNAUTHORIZED = new CodeMsg(401, "未授权");
    public static final CodeMsg FORBIDDEN = new CodeMsg(403, "禁止访问");
    public static final CodeMsg NOT_FOUND = new CodeMsg(404, "资源不存在");
    public static final CodeMsg METHOD_NOT_ALLOWED = new CodeMsg(405, "请求方法不支持");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500, "服务器内部错误");
    
    // 业务错误码 (5001xx)
    public static final CodeMsg BUSINESS_ERROR = new CodeMsg(500100, "业务处理异常");
    public static final CodeMsg DATA_NOT_FOUND = new CodeMsg(500101, "数据不存在");
    public static final CodeMsg DATA_ALREADY_EXISTS = new CodeMsg(500102, "数据已存在");
    
    // 用户相关错误码 (5002xx)
    public static final CodeMsg USER_NOT_FOUND = new CodeMsg(500201, "用户不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500202, "密码错误");
    public static final CodeMsg ACCOUNT_LOCKED = new CodeMsg(500203, "账号已锁定");
    public static final CodeMsg LOGIN_TIMEOUT = new CodeMsg(500204, "登录超时");
    
    // 权限相关错误码 (5003xx)
    public static final CodeMsg NO_PERMISSION = new CodeMsg(500301, "没有操作权限");
    
    /**
     * 构造函数
     *
     * @param code 错误码
     * @param msg  错误消息
     */
    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    /**
     * 自定义错误消息
     *
     * @param msg 错误消息
     * @return 自定义消息的错误码对象
     */
    public CodeMsg fillMsg(String msg) {
        return new CodeMsg(this.code, msg);
    }
}
