package com.vulcan.system.repository;

import com.vulcan.domain.entity.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.repository
 * @name: SysUserRepository
 * @Date: 2024/4/12 下午3:29
 * @Description 系统用户数据访问层
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    /**
     * 根据登录名查询用户
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    Optional<SysUser> findByLoginName(String loginName);
    
    /**
     * 根据登录名判断用户是否存在
     *
     * @param loginName 登录名
     * @return 是否存在
     */
    boolean existsByLoginName(String loginName);
}
