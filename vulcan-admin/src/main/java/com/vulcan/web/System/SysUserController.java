package com.vulcan.web.System;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.vulcan.entity.ResultVo;
import com.vulcan.entity.dto.SysUserDto;
import com.vulcan.entity.po.SysUser;
import com.vulcan.entity.vo.SysUserVo;
import com.vulcan.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.web.System
 * @name: SysUserController
 * @Date: 2024/4/12  下午3:36
 * @Description //TODO
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
