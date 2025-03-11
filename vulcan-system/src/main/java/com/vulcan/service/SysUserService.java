package com.vulcan.service;

import com.vulcan.entity.dto.SysUserDto;
import com.vulcan.entity.po.SysUser;
import com.vulcan.entity.vo.SysUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author Y
 * @Project: Smart-Factory
 * @Package: com.vulcan.service
 * @name: SysUserService
 * @Date: 2024/4/12 下午3:28
 * @Description 系统用户服务接口，定义用户查询、分页列表等功能
 */
public interface SysUserService {

    Optional<SysUser> findById(Long id);

    Page<SysUserVo> findAll(SysUserDto sysUserDto);

    Optional<SysUser> findByLoginName(String loginName);

}
