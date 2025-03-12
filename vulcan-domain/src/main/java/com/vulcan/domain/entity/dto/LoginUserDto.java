package com.vulcan.domain.entity.dto;

import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.dto
 * @name: LoginUser
 * @Date: 2024/4/13 下午6:44
 * @Description 登录用户数据传输对象，用于封装登录请求中的用户信息
 */
@Data
public class LoginUserDto {

    // 用户名
    String loginName;

    // 密码
    String password;

    // 登录类型
    String loginType;

}
