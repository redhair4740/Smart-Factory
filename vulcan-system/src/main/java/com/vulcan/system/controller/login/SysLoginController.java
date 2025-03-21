package com.vulcan.system.controller.login;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.vulcan.domain.entity.dto.LoginUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.system.service.SysUserService;
import com.vulcan.common.utils.security.EncryptionUtils;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.controller.login
 * @name: SysLoginController
 * @Date: 2024/4/12 下午3:37
 * @Description 系统登录控制器，处理用户登录相关的请求
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 登录
     * 
     * @param loginUserDto
     * @return
     */
    @SaIgnore
    @PostMapping("/login")
    public SaResult doLogin(@RequestBody LoginUserDto loginUserDto) {
        try {
            // 解密密码
            EncryptionUtils.decrypt(loginUserDto, "password", "password");
            
            // 创建一个 ModelMapper 对象
            ModelMapper modelMapper = new ModelMapper();
            SysUser sysUserParam = modelMapper.map(loginUserDto, SysUser.class);
            Optional<SysUser> sysUser = sysUserService.findByLoginName(sysUserParam.getLoginName());
            if (sysUser.isPresent()) {
                // 使用checkpw方法检查被加密的字符串是否与原始字符串匹配：
                if (BCrypt.checkpw(loginUserDto.getPassword(), sysUser.get().getPassword())) {
                    // 第二步：根据账号id，进行登录
                    StpUtil.login(sysUser.get().getId());
                    return SaResult.ok("登录成功");
                }
            }
            return SaResult.error("登录失败");
        } catch (Exception e) {
            return SaResult.error("登录异常：" + e.getMessage());
        }
    }

    /**
     * 刷新token
     */
    @GetMapping("/refresh")
    public void refresh() {
        // 先检查是否已被冻结
        // StpUtil.checkActiveTimeout();
        System.out.println(StpUtil.getTokenActiveTimeout());
        // 检查通过后继续续签
        StpUtil.updateLastActiveToNow();
    }
} 