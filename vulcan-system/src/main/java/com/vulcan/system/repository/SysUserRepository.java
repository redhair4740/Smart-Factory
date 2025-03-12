package com.vulcan.system.repository;

import com.vulcan.domain.entity.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.dao
 * @name: SysUserDao
 * @Date: 2024/4/12 下午3:29
 * @Description 系统用户数据访问接口，提供用户数据的CRUD操作和自定义查询方法
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    Optional<SysUser> findByLoginName(String loginName);

}
