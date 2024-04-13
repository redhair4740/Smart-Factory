package com.vulcan.service.impl;

import com.vulcan.repository.SysUserRepository;
import com.vulcan.entity.po.SysUser;
import com.vulcan.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service.impl
 * @name: SysUserServiceImpl
 * @Date: 2024/4/12  下午3:30
 * @Description //TODO
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserRepository sysUserRepository;

    /**
     * 根据id查询用户
     * @param id
     * @return Optional<SysUser>
     */
    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }

    /**
     * 根据登录名查询用户
     * @param loginName
     * @return Optional<SysUser>
     */
    @Override
    public Optional<SysUser> findByLoginName(String loginName) {
        return sysUserRepository.findByLoginName(loginName);
    }
}
