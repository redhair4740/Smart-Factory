package com.vulcan.entity.po;

import com.vulcan.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.entity.po
 * @name: SysUser
 * @Date: 2024/4/12  下午1:53
 * @Description //TODO
 */
@Data
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //用户编码
    @Column(name = "code")
    private String code;

    //用户名称
    @Column(name = "name")
    private String name;

    //登录名称
    @Column(name = "login_name")
    private String loginName;

    //登录密码
    @Column(name = "password")
    private String password;

    //手机号码
    @Column(name = "phone")
    private String phone;

    //邮箱
    @Column(name = "email")
    private String email;

    //工厂编码
    @Column(name = "plantCode")
    private String plant_code;

    //是否是超级管理员（0代表不是 1代表是）
    @Column(name = "super_admin_flag")
    private String superAdminFlag;

    //是否是工厂管理员（0代表不是 1代表是）
    @Column(name = "plant_admin_flag")
    private String plantAdminFlag;

}
