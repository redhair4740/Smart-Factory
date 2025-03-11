package com.vulcan.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.vulcan.entity.dto.LoginUserDto;
import com.vulcan.entity.po.SysUser;
import com.vulcan.service.SysUserService;
import com.vulcan.utils.security.EncryptionUtils;
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
 * @Date: 2024/4/26  上午10:47
 * @Description 认证控制器，负责处理用户登录认证等功能，使用Sa-Token框架实现身份验证
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 登录
     * @param loginUserDto
     * @return
     */
    @PostMapping("/login")
    public SaResult doLogin(@RequestBody LoginUserDto loginUserDto) {

        try {
            // 使用更新后的加密工具类进行解密
            EncryptionUtils.decrypt(loginUserDto, "password", "password");
        } catch (Exception e) {
            log.error("登录密码解密失败", e);
            return SaResult.error("登录失败");
        }

        // 创建一个 ModelMapper 对象
        ModelMapper modelMapper = new ModelMapper();
        SysUser sysUserParam = modelMapper.map(loginUserDto, SysUser.class);
        Optional<SysUser> sysUser = sysUserService.findByLoginName(sysUserParam.getLoginName());
        if(sysUser.isPresent()){
            // 使用checkpw方法检查被加密的字符串是否与原始字符串匹配：
            if(BCrypt.checkpw(loginUserDto.getPassword(), sysUser.get().getPassword())){
                // 第二步：根据账号id，进行登录
                StpUtil.login(sysUser.get().getId());
                SaTokenInfo saTokenInfo = StpUtil.getTokenInfo();
                return SaResult.data(saTokenInfo);
            }
        }
        return SaResult.error("登录失败");
    }

}