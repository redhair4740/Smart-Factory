package com.vulcan.system.service.impl;

import com.vulcan.domain.entity.dto.SysUserDto;
import com.vulcan.domain.entity.vo.SysUserVo;
import com.vulcan.system.repository.SysUserRepository;
import com.vulcan.domain.entity.po.SysUser;
import com.vulcan.system.service.SysUserService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    private SysUserRepository sysUserRepository;

    @Resource
    private ModelMapper modelMapper;

    /**
     * 根据id查询用户
     * 
     * @param id
     * @return Optional<SysUser>
     */
    @Override
    public Optional<SysUser> findById(Long id) {
        return sysUserRepository.findById(id);
    }

    @Override
    public Page<SysUserVo> findAll(SysUserDto sysUserDto) {
        // 构建分页参数
        Pageable pageable = PageRequest.of(
                sysUserDto.getPageNumber() == null ? 0 : sysUserDto.getPageNumber(),
                sysUserDto.getPageSize() == null ? 10 : sysUserDto.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );
        
        // 查询分页数据
        Page<SysUser> userPage = sysUserRepository.findAll(pageable);
        
        // 转换为VO
        return userPage.map(user -> modelMapper.map(user, SysUserVo.class));
    }

    /**
     * 根据登录名查询用户
     * 
     * @param loginName
     * @return Optional<SysUser>
     */
    @Override
    public Optional<SysUser> findByLoginName(String loginName) {
        return sysUserRepository.findByLoginName(loginName);
    }

    @Override
    @Transactional
    public SysUserVo createUser(SysUserDto sysUserDto) {
        // 转换DTO为实体
        SysUser sysUser = modelMapper.map(sysUserDto, SysUser.class);
        
        // 设置默认值
        if (sysUser.getSuperAdminFlag() == null) {
            sysUser.setSuperAdminFlag(0); // 默认非超级管理员
        }
        
        if (sysUser.getPlantAdminFlag() == null) {
            sysUser.setPlantAdminFlag(0); // 默认非工厂管理员
        }
        
        // 保存用户信息
        SysUser savedUser = sysUserRepository.save(sysUser);
        
        // 转换为VO并返回
        return modelMapper.map(savedUser, SysUserVo.class);
    }
    
    @Override
    public boolean existsByLoginName(String loginName) {
        return sysUserRepository.existsByLoginName(loginName);
    }
}
