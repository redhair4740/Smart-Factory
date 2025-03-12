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

        Specification<SysUser> spec = (sysUser, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (sysUserDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(sysUser.get("id"), sysUserDto.getId()));
            }
            if (StringUtils.isNotBlank(sysUserDto.getCode())) {
                predicates.add(criteriaBuilder.like(sysUser.get("code"), sysUserDto.getCode()));
            }
            if (StringUtils.isNotBlank(sysUserDto.getLoginName())) {
                predicates.add(criteriaBuilder.like(sysUser.get("loginName"), sysUserDto.getLoginName()));
            }
            if (StringUtils.isNotBlank(sysUserDto.getPhone())) {
                predicates.add(criteriaBuilder.equal(sysUser.get("phone"), sysUserDto.getPhone()));
            }
            if (StringUtils.isNotBlank(sysUserDto.getEmail())) {
                predicates.add(criteriaBuilder.equal(sysUser.get("email"), sysUserDto.getEmail()));
            }
            if (StringUtils.isNotBlank(sysUserDto.getPlantCode())) {
                predicates.add(criteriaBuilder.equal(sysUser.get("plantCode"), sysUserDto.getPlantCode()));
            }
            if (sysUserDto.getSuperAdminFlag() != null) {
                predicates.add(criteriaBuilder.equal(sysUser.get("superAdminFlag"), sysUserDto.getSuperAdminFlag()));
            }
            if (sysUserDto.getPlantAdminFlag() != null) {
                predicates.add(criteriaBuilder.equal(sysUser.get("plantAdminFlag"), sysUserDto.getPlantAdminFlag()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(sysUserDto.getPageNumber(), sysUserDto.getPageSize(),
                Sort.by("id").descending());
        Page<SysUser> sysUsers = sysUserRepository.findAll(spec, pageable);

        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(sysUsers, new TypeToken<Page<SysUserVo>>() {
        }.getType());
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
}
