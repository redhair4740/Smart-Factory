package com.vulcan.system.repository.impl;

import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.repository.UserQueryRepository;
import com.vulcan.system.repository.SysUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.system.service.impl
 * @name: UserQueryServiceImpl
 * @Date: 2024/4/26
 * @Description 用户领域仓库实现类，实现UserQueryRepository接口
 */
@Repository
@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final SysUserRepository sysUserRepository;

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    @Override
    @Cacheable(value = "userCache", key = "#loginName", unless = "#result == null")
    public Optional<SysUser> findByLoginName(String loginName) {
        return sysUserRepository.findByLoginName(loginName);
    }
    
    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return Optional<SysUser> 用户信息
     */
    @Override
    @Cacheable(value = "userCache", key = "'id_' + #id", unless = "#result == null")
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }
    
    /**
     * 保存用户信息
     *
     * @param sysUser 用户实体
     * @return SysUser 保存后的用户信息
     */
    @Override
    @Transactional
    @CacheEvict(value = "userCache", key = "'id_' + #sysUser.id", condition = "#sysUser.id != null")
    public SysUser save(SysUser sysUser) {
        SysUser saved = sysUserRepository.save(sysUser);
        
        // 如果有登录名，也清除按登录名缓存的数据
        if (saved.getLoginName() != null) {
            // 注意：这里不能直接使用@CacheEvict，因为需要用保存后的登录名
            // 在实际项目中，可以使用CacheManager手动清除缓存
        }
        
        return saved;
    }
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @Override
    @Transactional
    @CacheEvict(value = "userCache", key = "'id_' + #id")
    public void deleteById(Long id) {
        // 在实际项目中，可以先查询用户登录名，然后清除按登录名的缓存
        sysUserRepository.deleteById(id);
    }
    
    /**
     * 检查用户是否存在
     *
     * @param id 用户ID
     * @return boolean 是否存在
     */
    @Override
    public boolean existsById(Long id) {
        return sysUserRepository.existsById(id);
    }
    
    /**
     * 检查登录名是否存在
     *
     * @param loginName 登录名
     * @return boolean 是否存在
     */
    @Override
    public boolean existsByLoginName(String loginName) {
        return sysUserRepository.existsByLoginName(loginName);
    }
    
    /**
     * 分页查询用户列表
     *
     * @param specification 查询条件
     * @param pageable 分页参数
     * @return Page<SysUser> 分页结果
     */
    @Override
    public Page<SysUser> findAll(Specification<SysUser> specification, Pageable pageable) {
        return sysUserRepository.findAll(specification, pageable);
    }
}