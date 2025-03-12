package com.vulcan.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.vulcan.domain.entity.dto.LoginUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.service.UserQueryService;
import com.vulcan.common.utils.security.EncryptionUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.auth.controller
 * @name: AuthController
 * @Date: 2024/4/26 上午10:47
 * @Description 认证控制器，负责处理用户登录认证等功能，使用Sa-Token框架实现身份验证
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private UserQueryService userQueryService;

    /**
     * 登录
     * 
     * @param loginUserDto
     * @return
     */
    @PostMapping("/login")
    public SaResult doLogin(@RequestBody LoginUserDto loginUserDto) {
        // 解密密码
        if (!decryptPassword(loginUserDto)) {
            return SaResult.error("登录失败");
        }

        // 验证用户身份并执行登录
        return authenticateAndLogin(loginUserDto);
    }

    /**
     * 解密用户密码
     * 
     * @param loginUserDto 登录用户DTO
     * @return 解密是否成功
     */
    private boolean decryptPassword(LoginUserDto loginUserDto) {
        try {
            EncryptionUtils.decrypt(loginUserDto, "password", "password");
            return true;
        } catch (Exception e) {
            log.error("登录密码解密失败", e);
            return false;
        }
    }

    /**
     * 验证用户身份并执行登录
     * 
     * @param loginUserDto 登录用户DTO
     * @return 登录结果
     */
    private SaResult authenticateAndLogin(LoginUserDto loginUserDto) {
        // 转换DTO为实体对象
        ModelMapper modelMapper = new ModelMapper();
        SysUser sysUserParam = modelMapper.map(loginUserDto, SysUser.class);

        // 查找用户并验证
        Optional<SysUser> sysUser = userQueryService.findByLoginName(sysUserParam.getLoginName());

        if (sysUser.isPresent() && isPasswordValid(loginUserDto.getPassword(), sysUser.get().getPassword())) {
            return performLogin(sysUser.get());
        }

        return SaResult.error("登录失败");
    }

    /**
     * 验证密码是否正确
     * 
     * @param inputPassword  输入的密码
     * @param storedPassword 存储的密码
     * @return 密码是否匹配
     */
    private boolean isPasswordValid(String inputPassword, String storedPassword) {
        return BCrypt.checkpw(inputPassword, storedPassword);
    }

    /**
     * 执行登录操作
     * 
     * @param sysUser 系统用户
     * @return 登录结果
     */
    private SaResult performLogin(SysUser sysUser) {
        StpUtil.login(sysUser.getId());
        SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
        return SaResult.data(saTokenInfo);
    }

}