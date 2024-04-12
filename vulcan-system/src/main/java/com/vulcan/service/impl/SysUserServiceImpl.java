package com.vulcan.service.impl;

import com.vulcan.dao.SysUserDao;
import com.vulcan.entity.po.SysUser;
import com.vulcan.service.SysUserService;
import jakarta.annotation.Resource;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service.impl
 * @name: SysUserServiceImpl
 * @Date: 2024/4/12  下午3:30
 * @Description //TODO
 */
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    /**
     * 根据id查询用户
     * @param id
     * @return Optional<SysUser>
     */
    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserDao.findById(id);
    }
}
