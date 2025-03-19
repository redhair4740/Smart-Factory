package com.vulcan.system.controller.user;

import cn.dev33.satoken.annotation.SaIgnore;
import com.vulcan.domain.entity.ResultVo;
import com.vulcan.domain.entity.CodeMsg;
import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.vo.SysUserVo;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.common.utils.security.EncryptionUtils;
import com.vulcan.system.service.SysUserService;
import jakarta.annotation.Resource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.controller.user
 * @name: SysUserController
 * @Date: 2024/4/12  下午3:36
 * @Description 系统用户控制器，提供用户管理相关接口，包括用户列表查询等功能
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户列表
     */
    @PostMapping("/list")
    public ResultVo<Page<SysUserVo>> list(@RequestBody SysUserDto sysUserDto) {
        return ResultVo.success(sysUserService.findAll(sysUserDto));
    }

    /**
     * 用户注册
     * 
     * @param sysUserDto 用户信息
     * @return 注册结果
     */
    @SaIgnore
    @PostMapping("/register")
    public ResultVo<SysUserVo> register(@RequestBody SysUserDto sysUserDto) {
        try {
            // 解密前端传来的加密密码
            if (sysUserDto.getPassword() != null) {
                EncryptionUtils.decrypt(sysUserDto, "password", "password");
            } else {
                return ResultVo.error(CodeMsg.PASSWORD_EMPTY);
            }
            
            // 验证必填信息
            if (sysUserDto.getLoginName() == null || sysUserDto.getLoginName().isEmpty()) {
                return ResultVo.error(CodeMsg.USERNAME_EMPTY);
            }
            
            // 检查用户名是否已存在
            if (sysUserService.existsByLoginName(sysUserDto.getLoginName())) {
                return ResultVo.error(CodeMsg.USER_EXISTS);
            }
            
            // 进行密码加密存储（使用BCrypt）
            String hashedPassword = BCrypt.hashpw(sysUserDto.getPassword(), BCrypt.gensalt());
            sysUserDto.setPassword(hashedPassword);
            
            // 设置默认值
            if (sysUserDto.getName() == null || sysUserDto.getName().isEmpty()) {
                sysUserDto.setName(sysUserDto.getLoginName());
            }
            
            // 创建用户
            SysUserVo newUser = sysUserService.createUser(sysUserDto);
            return ResultVo.success(newUser);
        } catch (Exception e) {
            return ResultVo.error(CodeMsg.REGISTER_FAIL.fillMsg("注册失败: " + e.getMessage()));
        }
    }
    
    /**
     * 检查用户名是否可用
     * 
     * @param loginName 登录名
     * @return 是否可用
     */
    @SaIgnore
    @GetMapping("/check-username")
    public ResultVo<Boolean> checkUsername(@RequestParam("loginName") String loginName) {
        try {
            boolean exists = sysUserService.existsByLoginName(loginName);
            return ResultVo.success(!exists); // 返回true表示用户名可用，false表示已存在
        } catch (Exception e) {
            return ResultVo.error("检查用户名失败: " + e.getMessage());
        }
    }
} 