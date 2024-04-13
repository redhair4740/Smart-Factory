package com.vulcan.entity.dto;

import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.dto
 * @name: LoginUser
 * @Date: 2024/4/13 下午6:44
 * @Description //TODO
 */
@Data
public class LoginUserDto {

    //用户名
    String loginName;

    //密码
    String password;

    //登录类型
    String loginType;

}
