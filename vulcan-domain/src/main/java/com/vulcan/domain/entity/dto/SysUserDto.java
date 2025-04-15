package com.vulcan.domain.entity.dto;

import com.vulcan.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.dto
 * @name: SysUserDto
 * @Date: 2024/4/12 下午1:53
 * @Description 系统用户数据传输对象，用于封装用户信息的传输
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUserDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 用户编码
    private String code;

    // 用户名称
    private String name;

    // 登录名称
    private String loginName;

    // 密码
    private String password;

    // 手机号码
    private String phone;

    // 邮箱
    private String email;

    // 工厂编码
    private String plantCode;

    // 是否是超级管理员（0代表不是 1代表是）
    private Integer superAdminFlag;

    // 是否是工厂管理员（0代表不是 1代表是）
    private Integer plantAdminFlag;

    // 分页
    private Integer pageSize;

    // 分页
    private Integer pageNumber;

    // 签名
    private String sign;
}
