package com.vulcan.system.service;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.entity.vo.SysUserVo;
import org.springframework.data.domain.Page;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.service
 * @name: SysUserService
 * @Date: 2024/4/12 下午3:37
 * @Description 系统用户服务接口，提供用户相关的业务逻辑
 */
public interface SysUserService {

    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return Optional<SysUser> 用户信息
     */
    Optional<SysUser> findById(Long id);

    /**
     * 查询用户列表（分页）
     * 
     * @param sysUserDto 查询条件
     * @return Page<SysUserVo> 分页结果
     */
    Page<SysUserVo> findAll(SysUserDto sysUserDto);

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    Optional<SysUser> findByLoginName(String loginName);

    /**
     * 创建用户
     *
     * @param sysUserDto 用户数据
     * @return 创建后的用户信息
     */
    SysUserVo createUser(SysUserDto sysUserDto);

    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param sysUserDto 用户数据
     * @return 更新后的用户信息
     */
    SysUserVo updateUser(Long id, SysUserDto sysUserDto);
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long id);
    
    /**
     * 启用用户
     *
     * @param id 用户ID
     * @return 是否操作成功
     */
    boolean enableUser(Long id);
    
    /**
     * 禁用用户
     *
     * @param id 用户ID
     * @return 是否操作成功
     */
    boolean disableUser(Long id);

    /**
     * 检查登录名是否存在
     *
     * @param loginName 登录名
     * @return 是否存在
     */
    boolean existsByLoginName(String loginName);
}
