package com.vulcan.web.System;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.vulcan.entity.dto.LoginUserDto;
import com.vulcan.entity.po.SysUser;
import com.vulcan.service.SysUserService;
import jakarta.annotation.Resource;
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
 * @Package: com.vulcan.web.System
 * @name: AuthController
 * @Date: 2024/4/26  上午10:47
 * @Description //TODO
 */
@RestController
@RequestMapping("/auth")
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

        // 创建一个 ModelMapper 对象
        ModelMapper modelMapper = new ModelMapper();
        SysUser sysUserParam = modelMapper.map(loginUserDto, SysUser.class);
        Optional<SysUser> sysUser = sysUserService.findByLoginName(sysUserParam.getLoginName());
        if(sysUser.isPresent()){
            // 使用checkpw方法检查被加密的字符串是否与原始字符串匹配：
            if(BCrypt.checkpw(loginUserDto.getPassword(), sysUser.get().getPassword())){
                // 第二步：根据账号id，进行登录
                StpUtil.login(sysUser.get().getId());
                return SaResult.ok("登录成功");
            }
        }
        return SaResult.error("登录失败");
    }

}
