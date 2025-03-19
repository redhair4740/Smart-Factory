package com.vulcan.system.service;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.entity.vo.SysUserVo;
import org.springframework.data.domain.Page;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.service
 * @name: SysUserService
 * @Date: 2024/4/12 下午3:37
 * @Description 系统用户服务接口，提供用户相关的业务逻辑
 */
public interface SysUserService {

    Optional<SysUser> findById(Long id);

    Page<SysUserVo> findAll(SysUserDto sysUserDto);

    Optional<SysUser> findByLoginName(String loginName);

    /**
     * 创建用户
     *
     * @param sysUserDto 用户数据
     * @return 创建后的用户信息
     */
    SysUserVo createUser(SysUserDto sysUserDto);

    /**
     * 检查登录名是否存在
     *
     * @param loginName 登录名
     * @return 是否存在
     */
    boolean existsByLoginName(String loginName);
}
