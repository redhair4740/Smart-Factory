package com.vulcan.system.controller.user;

import com.vulcan.entity.ResultVo;
import com.vulcan.entity.dto.SysUserDto;
import com.vulcan.entity.vo.SysUserVo;
import com.vulcan.service.SysUserService;
import jakarta.annotation.Resource;
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

} 