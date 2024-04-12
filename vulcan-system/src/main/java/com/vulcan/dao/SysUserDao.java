package com.vulcan.dao;

import com.vulcan.entity.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.dao
 * @name: SysUserDao
 * @Date: 2024/4/12  下午3:29
 * @Description //TODO
 */
public interface SysUserDao extends JpaRepository<SysUser,Long> {
}
