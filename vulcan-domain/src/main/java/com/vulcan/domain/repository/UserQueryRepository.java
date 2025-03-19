package com.vulcan.domain.repository;

import com.vulcan.domain.entity.po.SysUser;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service
 * @name: UserQueryService
 * @Date: 2024/4/26
 * @Description 用户查询服务接口，定义用户查询相关的方法
 */
public interface UserQueryRepository {

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    Optional<SysUser> findByLoginName(String loginName);
}