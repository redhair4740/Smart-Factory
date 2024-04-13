package com.vulcan.service;

import com.vulcan.entity.po.SysUser;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service
 * @name: SysUserService
 * @Date: 2024/4/12  下午3:28
 * @Description //TODO
 */
public interface SysUserService {

    Optional<SysUser> findById(Long id);

    Optional<SysUser> findByLoginName(String loginName);

}
