package com.vulcan.repository;

import com.vulcan.entity.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.dao
 * @name: SysUserDao
 * @Date: 2024/4/12  下午3:29
 * @Description //TODO
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    Optional<SysUser> findByLoginName(String loginName);

}
