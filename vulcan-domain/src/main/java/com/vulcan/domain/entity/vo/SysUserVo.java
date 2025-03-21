package com.vulcan.domain.entity.vo;

import com.vulcan.domain.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.vo
 * @name: SysUserVo
 * @Date: 2024/4/12 下午1:53
 * @Description 系统用户视图对象，用于前端展示用户信息，不包含敏感字段如密码
 */
@Data
public class SysUserVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 用户编码
    private String code;

    // 用户名称
    private String name;

    // 登录名称
    private String loginName;

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

}
