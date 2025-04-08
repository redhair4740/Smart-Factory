package com.vulcan.system.service.impl;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.param.CodeRuleParam;
import com.vulcan.domain.entity.vo.SysUserVo;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.domain.repository.CodeQueryRepository;
import com.vulcan.domain.repository.UserQueryRepository;
import com.vulcan.system.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service.impl
 * @name: SysUserServiceImpl
 * @Date: 2024/4/12 下午3:30
 * @Description 系统用户服务实现类，提供用户查询、分页列表等功能的具体实现
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private UserQueryRepository userRepository;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private CodeQueryRepository codeRepository;

    /**
     * 根据id查询用户
     * 
     * @param id 用户ID
     * @return Optional<SysUser> 用户信息
     */
    @Override
    public Optional<SysUser> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 分页查询用户列表
     * 
     * @param sysUserDto 查询条件
     * @return Page<SysUserVo> 分页结果
     */
    @Override
    public Page<SysUserVo> findAll(SysUserDto sysUserDto) {
        // 构建分页参数
        Pageable pageable = buildPageable(sysUserDto);
        
        // 查询分页数据
        Page<SysUser> userPage = userRepository.findAll(createSpecification(sysUserDto), pageable);
        
        // 转换为VO
        return userPage.map(user -> modelMapper.map(user, SysUserVo.class));
    }
    
    /**
     * 构建分页参数
     */
    private Pageable buildPageable(SysUserDto sysUserDto) {
        return PageRequest.of(
                sysUserDto.getPageNumber() == null ? 0 : sysUserDto.getPageNumber(),
                sysUserDto.getPageSize() == null ? 10 : sysUserDto.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );
    }
    
    /**
     * 创建查询条件
     */
    private Specification<SysUser> createSpecification(SysUserDto sysUserDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // 根据条件添加过滤
            if (sysUserDto != null) {
                if (StringUtils.isNotBlank(sysUserDto.getLoginName())) {
                    predicates.add(criteriaBuilder.like(root.get("loginName"), 
                            "%" + sysUserDto.getLoginName() + "%"));
                }
                
                if (StringUtils.isNotBlank(sysUserDto.getName())) {
                    predicates.add(criteriaBuilder.like(root.get("name"),
                            "%" + sysUserDto.getName() + "%"));
                }
                
                if (sysUserDto.getStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), sysUserDto.getStatus()));
                }
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * 根据登录名查询用户
     * 
     * @param loginName 登录名
     * @return Optional<SysUser> 用户信息
     */
    @Override
    public Optional<SysUser> findByLoginName(String loginName) {
        return userRepository.findByLoginName(loginName);
    }

    /**
     * 创建用户
     * 
     * @param sysUserDto 用户数据
     * @return SysUserVo 创建后的用户信息
     */
    @Override
    @Transactional
    public SysUserVo createUser(SysUserDto sysUserDto) {
        // 验证用户数据
        validateUserData(sysUserDto);
        
        // 转换DTO为实体
        SysUser sysUser = modelMapper.map(sysUserDto, SysUser.class);
        
        // 设置默认值
        setDefaultValues(sysUser);

        // 设置编码
        CodeRuleParam param = new CodeRuleParam();
        param.setRuleCode("USER_CODE");
        sysUser.setCode(codeRepository.generateCode(param));
        
        // 保存用户信息
        SysUser savedUser = userRepository.save(sysUser);
        
        // 转换为VO并返回
        return modelMapper.map(savedUser, SysUserVo.class);
    }
    
    /**
     * 验证用户数据
     */
    private void validateUserData(SysUserDto sysUserDto) {
        if (sysUserDto == null) {
            throw new IllegalArgumentException("用户数据不能为空");
        }
        
        if (StringUtils.isBlank(sysUserDto.getLoginName())) {
            throw new IllegalArgumentException("登录名不能为空");
        }
        
        if (StringUtils.isBlank(sysUserDto.getPassword())) {
            throw new IllegalArgumentException("密码不能为空");
        }
        
        // 检查登录名是否已存在
        if (existsByLoginName(sysUserDto.getLoginName())) {
            throw new IllegalArgumentException("登录名已存在: " + sysUserDto.getLoginName());
        }
    }
    
    /**
     * 设置默认值
     */
    private void setDefaultValues(SysUser sysUser) {
        if (sysUser.getSuperAdminFlag() == null) {
            sysUser.setSuperAdminFlag(0); // 默认非超级管理员
        }
        
        if (sysUser.getPlantAdminFlag() == null) {
            sysUser.setPlantAdminFlag(0); // 默认非工厂管理员
        }
        
        if (sysUser.getStatus() == null) {
            sysUser.setStatus("1"); // 默认启用状态
        }
    }
    
    /**
     * 检查登录名是否已存在
     * 
     * @param loginName 登录名
     * @return boolean 是否存在
     */
    @Override
    public boolean existsByLoginName(String loginName) {
        return userRepository.existsByLoginName(loginName);
    }
    
    /**
     * 更新用户
     *
     * @param id 用户ID
     * @param sysUserDto 用户数据
     * @return 更新后的用户信息
     */
    @Override
    @Transactional
    public SysUserVo updateUser(Long id, SysUserDto sysUserDto) {
        // 验证参数
        if (id == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        if (sysUserDto == null) {
            throw new IllegalArgumentException("用户数据不能为空");
        }
        
        // 查询现有用户
        SysUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在：" + id));
        
        // 验证登录名是否被其他用户占用
        if (!existingUser.getLoginName().equals(sysUserDto.getLoginName()) 
                && existsByLoginName(sysUserDto.getLoginName())) {
            throw new IllegalArgumentException("登录名已被占用：" + sysUserDto.getLoginName());
        }
        
        // 更新用户信息（保留不应被修改的字段）
        String originalPassword = existingUser.getPassword();
        
        // 映射DTO到实体
        modelMapper.map(sysUserDto, existingUser);
        
        // 如果密码为空，保留原密码
        if (StringUtils.isBlank(existingUser.getPassword())) {
            existingUser.setPassword(originalPassword);
        }
        
        // 保留ID
        existingUser.setId(id);
        
        // 保存更新
        SysUser updatedUser = userRepository.save(existingUser);
        
        // 转换为VO返回
        return modelMapper.map(updatedUser, SysUserVo.class);
    }
    
    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否删除成功
     */
    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        // 验证参数
        if (id == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        // 检查用户是否存在
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("用户不存在：" + id);
        }
        
        try {
            // 删除用户
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 启用用户
     *
     * @param id 用户ID
     * @return 是否操作成功
     */
    @Override
    @Transactional
    public boolean enableUser(Long id) {
        return updateUserStatus(id, "1");
    }
    
    /**
     * 禁用用户
     *
     * @param id 用户ID
     * @return 是否操作成功
     */
    @Override
    @Transactional
    public boolean disableUser(Long id) {
        return updateUserStatus(id, "0");
    }
    
    /**
     * 更新用户状态
     *
     * @param id 用户ID
     * @param status 状态(0-禁用，1-启用)
     * @return 是否更新成功
     */
    private boolean updateUserStatus(Long id, String status) {
        // 验证参数
        if (id == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        // 查询现有用户
        SysUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在：" + id));
        
        // 更新状态
        user.setStatus(status);
        
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
