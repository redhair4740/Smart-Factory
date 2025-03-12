package com.vulcan.system.service.impl;

import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.service.UserQueryService;
import com.vulcan.system.repository.SysUserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.service.impl
 * @name: UserQueryServiceImpl
 * @Date: 2024/4/26
 * @Description 用户查询服务实现类，实现UserQueryService接口
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Resource
    private SysUserRepository sysUserRepository;

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    @Override
    public Optional<SysUser> findByLoginName(String loginName) {
        return sysUserRepository.findByLoginName(loginName);
    }
}