package com.vulcan.domain.repository;

import com.vulcan.domain.entity.po.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.domain.repository
 * @name: UserQueryRepository
 * @Date: 2024/4/26
 * @Description 用户领域仓库接口，定义用户领域操作的方法
 */
public interface UserQueryRepository {

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    Optional<SysUser> findByLoginName(String loginName);
    
    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return Optional<SysUser> 用户信息
     */
    Optional<SysUser> findById(Long id);
    
    /**
     * 保存用户信息
     *
     * @param sysUser 用户实体
     * @return SysUser 保存后的用户信息
     */
    SysUser save(SysUser sysUser);
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteById(Long id);
    
    /**
     * 检查用户是否存在
     *
     * @param id 用户ID
     * @return boolean 是否存在
     */
    boolean existsById(Long id);
    
    /**
     * 检查登录名是否存在
     *
     * @param loginName 登录名
     * @return boolean 是否存在
     */
    boolean existsByLoginName(String loginName);
    
    /**
     * 分页查询用户列表
     *
     * @param specification 查询条件
     * @param pageable 分页参数
     * @return Page<SysUser> 分页结果
     */
    Page<SysUser> findAll(Specification<SysUser> specification, Pageable pageable);
}